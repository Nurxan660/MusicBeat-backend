package com.example.musicBeat.compositeKey;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

@Embeddable
@Data
public class MusicCategoryKey {

    private Long musicId;
    private Long categoryId;

}
