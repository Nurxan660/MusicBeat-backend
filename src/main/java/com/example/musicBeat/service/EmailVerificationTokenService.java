package com.example.musicBeat.service;


import com.example.musicBeat.entity.EmailVerificationToken;
import com.example.musicBeat.entity.User;
import com.example.musicBeat.exception.EmailConfirmedException;
import com.example.musicBeat.exception.TokenExpiredException;
import com.example.musicBeat.repository.EmailVerificationTokenRepository;
import com.example.musicBeat.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationTokenService {
    @Autowired
    EmailVerificationTokenRepository emailVerificationTokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String createToken(){
        return UUID.randomUUID().toString();
    }

    public String saveToken(User user, String token){
        EmailVerificationToken emailVerificationToken=new EmailVerificationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user);
        EmailVerificationToken res=emailVerificationTokenRepository.save(emailVerificationToken);
        return  res.getToken();


    }
    public EmailVerificationToken getToken(String token){
        return emailVerificationTokenRepository.findByToken(token).orElseThrow(()->new RuntimeException("token not found"));

    }

    public void confirmTokenAndChangePassword(String token) throws TokenExpiredException, EmailConfirmedException {
        EmailVerificationToken emailVerificationToken=getToken(token);
        if(emailVerificationToken.getConfirmedAt()!=null){
            throw new EmailConfirmedException("Link already used");
        }
        if(emailVerificationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("Link expired");
        }
        emailVerificationToken.setConfirmedAt(LocalDateTime.now());
        emailVerificationTokenRepository.save(emailVerificationToken);
        String newPassword=createNewPassword();
        User user=emailVerificationToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        mailService.send(emailVerificationToken.getUser().getEmail(),"password "+newPassword,"Your new password");

    }

    public String createNewPassword(){
        String password= RandomStringUtils.randomAlphabetic(12);
        return password;
    }















}
