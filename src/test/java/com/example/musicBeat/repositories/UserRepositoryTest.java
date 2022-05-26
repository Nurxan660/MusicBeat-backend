package com.example.musicBeat.repositories;

import com.example.musicBeat.entity.User;
import com.example.musicBeat.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;


    private static final String  EMAIL="musicBeatTest@mail.ru";
    private static final String  NICKNAME="musicBeatTest";
    private static final String  PASSWORD="Blabla";

    @Test
    public void findByEmail(){
        User user=new User(NICKNAME,EMAIL,PASSWORD);
        testEntityManager.persist(user);

        final Optional<User> actual=userRepository.findByEmail(EMAIL);

        assertTrue(actual.isPresent());
        assertEquals(user,actual.get());

    }
    @Test
    public void existByEmail(){
        User user=new User(NICKNAME,EMAIL,PASSWORD);
        testEntityManager.persist(user);

        boolean actual =userRepository.existsByEmail(EMAIL);

        assertTrue(actual);

    }

    @Test
    public void existsByNickname(){
        User user=new User(NICKNAME,EMAIL,PASSWORD);
        testEntityManager.persist(user);

        boolean actual =userRepository.existsByNickname(NICKNAME);

        assertTrue(actual);


    }
}
