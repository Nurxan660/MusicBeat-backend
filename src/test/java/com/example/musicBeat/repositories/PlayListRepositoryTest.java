package com.example.musicBeat.repositories;

import com.example.musicBeat.entity.PlayList;
import com.example.musicBeat.entity.RefreshToken;
import com.example.musicBeat.entity.User;
import com.example.musicBeat.repository.PlaylistRepository;
import com.example.musicBeat.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayListRepositoryTest {


    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private PlaylistRepository playlistRepository;


    @Test
    public void findByUniqueAddress(){
        User user=new User("Asfasfasfasf","sagasgag@asf.ru","124dgdsgsdgsd");
        testEntityManager.persist(user);
        PlayList playList=new PlayList("asfasf","Afsdgewrgwegweg",user);
        testEntityManager.persist(playList);

        Optional<PlayList> actual=playlistRepository.findByUniqueAddress("Afsdgewrgwegweg");

        assertEquals(playList,actual.get());
    }


    @Test
    public void findByUserId(){
        User user=new User("Asfasfasfasf","sagasgag@asf.ru","124dgdsgsdgsd");
        testEntityManager.persist(user);
        PlayList playList=new PlayList("asfasf","Afsdgewrgwegweg",user);
        PlayList res=testEntityManager.persist(playList);
        Pageable pageable= PageRequest.of(0,6);

        Page<PlayList> actual=playlistRepository.findByUserId(res.getUser().getId(),pageable);

        assertEquals(playList,actual.getContent().get(0));
    }

    @Test
    public void findByUserEmail(){
        User user=new User("Asfasfasfasf","sagasgag@asf.ru","124dgdsgsdgsd");
        testEntityManager.persist(user);
        PlayList playList=new PlayList("asfasf","Afsdgewrgwegweg",user);
        PlayList res=testEntityManager.persist(playList);

            List<PlayList> actual=playlistRepository.findByUserEmail(res.getUser().getEmail());

        assertEquals(playList,actual.get(0));
    }
}
