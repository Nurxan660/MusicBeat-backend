package com.example.musicBeat.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long playListId;
    @NonNull
    private String playListName;
    @NonNull
    @Column(unique = true)
    private String uniqueAddress;
    @ManyToOne
    @JoinColumn(name="user_id")
    @NonNull
    private User user;
}
