package com.example.musicBeat.service;

import com.example.musicBeat.dto.LoginRequest;
import com.example.musicBeat.dto.LoginResponse;
import com.example.musicBeat.dto.RegistrationRequest;
import com.example.musicBeat.dto.RegistrationResponse;
import com.example.musicBeat.entity.ERole;
import com.example.musicBeat.entity.RefreshToken;
import com.example.musicBeat.entity.Role;
import com.example.musicBeat.entity.User;
import com.example.musicBeat.repository.RefreshTokenRepository;
import com.example.musicBeat.repository.RoleRepository;
import com.example.musicBeat.repository.UserRepository;
import com.example.musicBeat.security.UserDetailsImpl;
import com.example.musicBeat.security.jwt.JwtProvider;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AuthService.class)
public class AuthServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private EmailVerificationTokenService emailVerificationTokenService;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private RefreshTokenRepository refreshTokenRepository;
    @MockBean
    private MailService mailService;
    @Autowired
    private AuthService authService;
    @Test
    public void registration(){
        Set<String> set=new HashSet<>();
        set.add("user");
        RegistrationRequest registrationRequest =new RegistrationRequest("asfasf","ASfasf","Asfasf", set);
        RegistrationResponse res=new RegistrationResponse("User sucessfully registered",registrationRequest.getEmail(),registrationRequest.getNickname());
        when(roleRepository.findByRole(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(1L, ERole.ROLE_USER)));
        when(roleRepository.findByRole(ERole.ROLE_ADMIN)).thenReturn(Optional.of(new Role(2L, ERole.ROLE_ADMIN)));
        when(passwordEncoder.encode(registrationRequest.getPassword())).thenReturn("#@$#%@%@#^#^^^^@#^@#^@#");

        RegistrationResponse resService=authService.registration(registrationRequest);

        assertThat(res).isEqualToComparingFieldByField(resService);
    }

    @Test
    public void login(){
        List<SimpleGrantedAuthority> list=new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        List<String> role=new ArrayList<>();
        role.add("ROLE_USER");
        final String access="safasfasgereehrthrthrt";
        final String refresh="askmldfqwofjqmlwdqwklkqwmd";
        final String message="Successfully sign in";
        LoginRequest req=new LoginRequest("nur@mail.ru","asf;lasfas");
            Authentication authentication=mock(UsernamePasswordAuthenticationToken.class);
        UserDetailsImpl userDetails=new UserDetailsImpl(1L,"asfasf",req.getEmail(),req.getPassword(),list);
            when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword())))
                    .thenReturn(new UsernamePasswordAuthenticationToken(userDetails,null));
            when(jwtProvider.generateToken(userDetails.getEmail())).thenReturn(access);
            when(refreshTokenService.createRefreshToken(userDetails.getEmail())).thenReturn(refresh);

        LoginResponse res=authService.signIn(req);

        assertThat(new LoginResponse(access,refresh,userDetails.getUsername(),userDetails.getId(),userDetails.getEmail(),message,role))
                .isEqualToComparingFieldByField(res);
    }

    @Test
    public void logout(){
        final Long id=1L;
        doNothing().when(refreshTokenRepository).deleteByUserId(id);

        authService.logout(id);
    }

    @Test
    public void restorePassword(){
        Set<Role> set=new HashSet<>();
        set.add(new Role(1L,ERole.ROLE_USER));
        final String email="nurxan@mail.ru";
        final String token="Asfasfasf";
        User user=new User(1L,"nurxan",email,"Asfasfasf",new Date(),null,set);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(emailVerificationTokenService.saveToken(user,token)).thenReturn(token);

        authService.restorePassword(email);


    }

}
