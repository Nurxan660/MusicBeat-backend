package com.example.musicBeat.repository;

import com.example.musicBeat.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<PlayList,Long> {
    Optional<PlayList> findByUniqueAddress(String uniqueAddress);
}