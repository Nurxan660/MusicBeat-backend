package com.example.musicBeat.service;


import com.example.musicBeat.dto.TokenRefreshResponse;
import com.example.musicBeat.entity.RefreshToken;
import com.example.musicBeat.entity.User;
import com.example.musicBeat.exception.TokenExpiredException;
import com.example.musicBeat.exception.TokenNotFoundException;
import com.example.musicBeat.repository.RefreshTokenRepository;
import com.example.musicBeat.repository.UserRepository;
import com.example.musicBeat.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtProvider jwtProvider;

    public String createRefreshToken(String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("email not found"));
        RefreshToken refreshToken=refreshTokenRepository.findByUserId(user.getId()).orElse(new RefreshToken());
        if(refreshToken.getToken()==null){
            refreshToken.setUser(user);
            refreshToken.setExpiredDate(LocalDateTime.now().plusHours(8));
            refreshToken.setToken(generateRefreshToken());
        }
        else{
            refreshToken.setToken(generateRefreshToken());

        }
        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }
    public String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }
    public TokenRefreshResponse checkExpiration(String token) throws TokenExpiredException {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token).orElseThrow(()->new TokenNotFoundException("token not found"));
        if(refreshToken.getExpiredDate().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("refresh token expired , please sign in ");
        }
        String newRefreshToken=generateRefreshToken();
        refreshToken.setToken(newRefreshToken);
        refreshToken.setExpiredDate(LocalDateTime.now().plusHours(8));
        refreshTokenRepository.save(refreshToken);
        String newJwtToken=jwtProvider.generateToken(refreshToken.getUser().getEmail());
        return new TokenRefreshResponse(newJwtToken,newRefreshToken);
    }




}
