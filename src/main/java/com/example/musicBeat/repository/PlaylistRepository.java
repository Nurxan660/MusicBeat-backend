package com.example.musicBeat.repository;

import com.example.musicBeat.entity.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<PlayList,Long> {
    Optional<PlayList> findByUniqueAddress(String uniqueAddress);
    Page<PlayList> findByUserId(Long id, Pageable pageable);
    List<PlayList> findByUserEmail(String email);

}
