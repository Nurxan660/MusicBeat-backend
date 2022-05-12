package com.example.musicBeat.entity;


import com.example.musicBeat.compositeKey.MusicCategoryKey;
import com.example.musicBeat.compositeKey.MusicPlayListKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicPlaylist {

    @EmbeddedId
    private MusicPlayListKey musicPlayListKey;

    @ManyToOne
    @JoinColumn(name="music_id")
    @MapsId("musicId")
    private Music music;
    @ManyToOne
    @JoinColumn(name="playlist_id")
    @MapsId("playListId")
    @JsonIgnore
    private PlayList playList;
}
