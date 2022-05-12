package com.example.musicBeat.compositeKey;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicPlayListKey implements Serializable {

    private Long musicId;
    private Long playListId;
}
