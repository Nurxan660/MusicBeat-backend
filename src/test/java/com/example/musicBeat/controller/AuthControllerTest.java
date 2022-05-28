package com.example.musicBeat.controller;


import com.example.musicBeat.dto.*;
import com.example.musicBeat.security.UserDetailsServiceImpl;
import com.example.musicBeat.security.jwt.AuthEntryPointJwt;
import com.example.musicBeat.security.jwt.JwtProvider;
import com.example.musicBeat.service.AuthService;
import com.example.musicBeat.service.AuthServiceTest;
import com.example.musicBeat.service.EmailVerificationTokenService;
import com.example.musicBeat.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
 public class AuthControllerTest {
    @MockBean
    private AuthService authService;
    @MockBean
    private EmailVerificationTokenService emailVerificationTokenService;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void signup() throws Exception {
         Set<String> set=new HashSet<>();
         set.add("ROLE_USER");
        RegistrationRequest req=new RegistrationRequest("blabla","blabla@mail.ru","12345678",set);
        RegistrationResponse res=new RegistrationResponse("User sucessfully registered",req.getEmail(),req.getNickname());
        when(authService.registration(any(RegistrationRequest.class))).thenReturn(res);

        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(res.getMessage()))
                .andExpect(jsonPath("$.email").value(res.getEmail()))
                .andExpect(jsonPath("$.nickname").value(res.getNickname()))
                .andDo(print());
    }

    @Test
    public void signin() throws Exception {
        List<String> list=new ArrayList<>();
        list.add("ROLE_USER");
        LoginRequest req=new LoginRequest("nurxan@mail.ru","12345678");
        LoginResponse res=new LoginResponse("safasfasfasf","safasfasfasf","nurxan",1L ,req.getEmail(),"signin",list);
        when(authService.signIn(any(LoginRequest.class))).thenReturn(res);

        mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(res.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(res.getRefreshToken()))
                .andExpect(jsonPath("$.nickname").value(res.getNickname()))
                .andExpect(jsonPath("$.id").value(res.getId()))
                .andExpect(jsonPath("$.email").value(res.getEmail()))
                .andExpect(jsonPath("$.message").value(res.getMessage()))
                .andExpect(jsonPath("$.roles[0]").value(res.getRoles().get(0)))
                .andDo(print());
    }

    @Test
    public void logout() throws Exception {
        final long id=1;
        doNothing().when(authService).logout(id);
        mockMvc.perform(delete("/auth/logout?userId={id}",id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Successfully logout"))
                .andDo(print());


    }

    @Test
    public void refreshToken() throws Exception {
        RefreshTokenRequest refreshTokenRequest=new RefreshTokenRequest("dmngsnmdngskdjg");
        TokenRefreshResponse tokenRefreshResponse=new TokenRefreshResponse("svfasfasfasf","ASfasfasf");

        when(refreshTokenService.checkExpiration(refreshTokenRequest.getRefreshToken())).thenReturn(tokenRefreshResponse);

        mockMvc.perform(post("/auth/refreshToken").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshTokenRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(tokenRefreshResponse.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(tokenRefreshResponse.getRefreshToken()))
                .andDo(print());
    }


    @Test
    public void restorePassword() throws Exception {
        RestorePasswordRequest restorePasswordRequest=new RestorePasswordRequest("saffs@asfsf.ru");

        doNothing().when(authService).restorePassword(restorePasswordRequest.getEmail());

        mockMvc.perform(post("/auth/restorePassword").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restorePasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("A link has been sent to your email"))
                .andDo(print());
    }

    @Test
    public void confirmToken() throws Exception {
        final String token="fsasfasfsafasf";
        mockMvc.perform(get("/auth/confirmToken?token={token}",token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("New password sended to your email"))
                .andDo(print());
    }
}
