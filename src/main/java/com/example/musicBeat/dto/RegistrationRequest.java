package com.example.musicBeat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
public class RegistrationRequest {
    private String nickname;
    private String email;
    private String password;
    private Set<String> role;



}
