package com.example.musicBeat.repositories;


import com.example.musicBeat.entity.RefreshToken;
import com.example.musicBeat.entity.User;
import com.example.musicBeat.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RefreshTokenRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Test
    public void findByToken(){
        User user=new User("asfasf","safasf@safs.ru","sfasfasfasf");
        testEntityManager.persist(user);
        RefreshToken refreshToken=new RefreshToken(user,"ASfasfasfasfasf", LocalDateTime.now());
        testEntityManager.persist(refreshToken);

        Optional<RefreshToken> actual= refreshTokenRepository.findByToken("ASfasfasfasfasf");

        assertEquals(refreshToken,actual.get());
    }

    @Test
    public void deleteByUserId(){
        User user=new User("asfasf","safasf@safs.ru","sfasfasfasf");
        testEntityManager.persist(user);
        RefreshToken refreshToken=new RefreshToken(user,"ASfasfasfasfasf", LocalDateTime.now());
        RefreshToken result=testEntityManager.persist(refreshToken);

        refreshTokenRepository.deleteByUserId(result.getUser().getId());

        Optional<RefreshToken> actual= refreshTokenRepository.findByToken("ASfasfasfasfasf");
        assertFalse(actual.isPresent());

    }

    @Test
    public void findByUserId(){
        User user=new User("asfasf","safasf@safs.ru","sfasfasfasf");
        testEntityManager.persist(user);
        RefreshToken refreshToken=new RefreshToken(user,"ASfasfasfasfasf", LocalDateTime.now());
        RefreshToken result=testEntityManager.persist(refreshToken);

        Optional<RefreshToken> actual= refreshTokenRepository.findByUserId(result.getUser().getId());

        assertEquals(refreshToken,actual.get());
    }
}
