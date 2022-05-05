package com.example.musicBeat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MusicCategory {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long musicCategoryId;
    private String musicCategoryName;

    public Long getMusicCategoryId() {
        return musicCategoryId;
    }

    public void setMusicCategoryId(Long musicCategoryId) {
        this.musicCategoryId = musicCategoryId;
    }

    public String getMusicCategoryName() {
        return musicCategoryName;
    }

    public void setMusicCategoryName(String musicCategoryName) {
        this.musicCategoryName = musicCategoryName;
    }
}
