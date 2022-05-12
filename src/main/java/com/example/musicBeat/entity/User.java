package com.example.musicBeat.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @Column(unique = true,nullable = false)
    @NonNull
    private String nickname;
    @Column(unique = true,nullable = false)
    @NonNull
    private String email;
    @Column(nullable = false)
    @NonNull
    @JsonIgnore
    private String password;
    private Date date;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<EmailVerificationToken> tokens;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    @JsonIgnore
    private Set<Role> roles;

}
