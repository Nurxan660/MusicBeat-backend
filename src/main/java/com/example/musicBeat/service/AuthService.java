package com.example.musicBeat.service;



import com.example.musicBeat.dto.LoginRequest;
import com.example.musicBeat.dto.LoginResponse;
import com.example.musicBeat.dto.RegistrationRequest;
import com.example.musicBeat.entity.*;
import com.example.musicBeat.exception.EmailAlreadyExistException;
import com.example.musicBeat.exception.EmailAndNicknameAlreadyExistException;
import com.example.musicBeat.exception.NicknameAlreadyExistException;
import com.example.musicBeat.repository.RefreshTokenRepository;
import com.example.musicBeat.repository.RoleRepository;
import com.example.musicBeat.repository.UserRepository;
import com.example.musicBeat.security.UserDetailsImpl;
import com.example.musicBeat.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MailService mailService;
    @Autowired
    EmailVerificationTokenService emailVerificationTokenService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public User registration(RegistrationRequest req) throws EmailAlreadyExistException, NicknameAlreadyExistException, EmailAndNicknameAlreadyExistException {
        if(userRepository.existsByEmail(req.getEmail())&&!userRepository.existsByNickname(req.getNickname())){
            throw new EmailAlreadyExistException("email already exist");
        }
         if(userRepository.existsByNickname(req.getNickname())&&!userRepository.existsByEmail(req.getEmail())){
            throw new NicknameAlreadyExistException("nickname already exist");
        }
        if(userRepository.existsByNickname(req.getNickname())&&userRepository.existsByEmail(req.getEmail())){
            throw new EmailAndNicknameAlreadyExistException("email and nickname already exist");
        }
        User user = new User(req.getNickname(), req.getEmail(), passwordEncoder.encode(req.getPassword()));
        Set<String> strRoles = req.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role == "admin") {
                    Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByRole(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setDate(new Date());
        userRepository.save(user);
        return user;


    }

    public LoginResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipals= (UserDetailsImpl) authentication.getPrincipal();
        String token=jwtProvider.generateToken(userPrincipals.getEmail());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(userDetails.getEmail());
        return new LoginResponse(token,refreshToken.getToken(),userDetails.getId(),userDetails.getEmail(),roles);
    }

    @Transactional
    public void logout(Long userId){
        refreshTokenRepository.deleteByUserId(userId);
    }

    public void restorePassword(String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("email invalid"));
        EmailVerificationToken token =emailVerificationTokenService.saveToken(user,emailVerificationTokenService.createToken());
        mailService.send(email,"Please follow to http://localhost:8080/auth/confirmToken?token="+token.getToken()+" to change the password","Password restore");
    }









}
