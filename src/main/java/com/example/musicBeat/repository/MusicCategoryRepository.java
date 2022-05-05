package com.example.musicBeat.repository;

import com.example.musicBeat.compositeKey.MusicCategoryKey;
import com.example.musicBeat.entity.MusicCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicCategoryRepository extends JpaRepository<MusicCategory, MusicCategoryKey> {
    List<MusicCategory> findByMusicCategoryKeyCategoryId(Long categoryId);
}
