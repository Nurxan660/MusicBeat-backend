package com.example.musicBeat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationResponse {

    private String message;
    private String email;
    private String nickname;


}
