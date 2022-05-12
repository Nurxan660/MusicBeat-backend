package com.example.musicBeat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMusicToPlaylistReq {
    private Long musicId;
    private String uniqueAddress;
}
