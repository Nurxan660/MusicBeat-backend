package com.example.musicBeat.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @OneToOne
    @JoinColumn(nullable = false,unique = true,name="user_id")
    private User user;
    @NonNull
    @Column(nullable = false,unique = true)
    private String token;
    @NonNull
    @Column(nullable = false)
    private LocalDateTime expiredDate;


}
