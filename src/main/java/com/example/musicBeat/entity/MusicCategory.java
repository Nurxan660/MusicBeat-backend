package com.example.musicBeat.entity;

import com.example.musicBeat.compositeKey.MusicCategoryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicCategory {
    @EmbeddedId
    private MusicCategoryKey musicCategoryKey;

    @ManyToOne
    @JoinColumn(name="music_id")
    @MapsId("musicId")
    private Music music;
    @ManyToOne
    @JoinColumn(name="category_id")
    @MapsId("categoryId")
    Category category;


}
