package com.example.musicBeat.repository;

import com.example.musicBeat.entity.MainCategory;
import com.example.musicBeat.entity.MusicCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainCategoryRepository extends JpaRepository<MainCategory,Long> {
    List<MainCategory> findAllByOrderByMainCategoryId();
}
