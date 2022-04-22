package com.example.musicBeat.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(nullable = false,unique = true,name="user_id")
    private User user;
    @Column(nullable = false,unique = true)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expiredDate;


}
