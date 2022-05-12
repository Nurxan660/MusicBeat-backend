package com.example.musicBeat.repository;

import com.example.musicBeat.compositeKey.MusicPlayListKey;
import com.example.musicBeat.entity.MusicPlaylist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicPlaylistRepository extends JpaRepository<MusicPlaylist, MusicPlayListKey> {
    List<MusicPlaylist> findByPlayListUniqueAddress(String uniqueAddress);
    Page<MusicPlaylist> findByPlayListUniqueAddress(String uniqueAddress, Pageable pageable);
    void deleteByMusicPlayListKey(MusicPlayListKey musicPlayListKey);
}
