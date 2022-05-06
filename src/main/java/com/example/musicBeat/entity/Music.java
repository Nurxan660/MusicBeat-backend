package com.example.musicBeat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long musicId;
    private String url;
    private String name;
    private String author;
    private String duration;
    private String album;
    private String imageUrl;



}
