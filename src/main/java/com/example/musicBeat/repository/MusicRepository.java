package com.example.musicBeat.repository;

import com.example.musicBeat.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,Long> {
    @Query(value="select * from music where author||' '||name ilike %:pattern%",nativeQuery = true)
    Page<Music> findByPattern(@Param("pattern") String pattern, Pageable pageable);
    List<Music> findAllByOrderByMusicId();

}
