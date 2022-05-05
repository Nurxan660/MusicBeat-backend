package com.example.musicBeat.compositeKey;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class MusicCategoryKey implements Serializable {

    private Long musicId;
    private Long categoryId;

}
