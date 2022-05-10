package com.example.musicBeat.repository;

import com.example.musicBeat.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,Long> {
    List<Music> findAllByOrderByMusicId();
}
