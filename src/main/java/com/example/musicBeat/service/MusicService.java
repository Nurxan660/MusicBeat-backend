package com.example.musicBeat.service;


import com.example.musicBeat.entity.Category;
import com.example.musicBeat.entity.MusicCategory;
import com.example.musicBeat.repository.CategoryRepository;
import com.example.musicBeat.repository.MusicCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MusicCategoryRepository musicCategoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public List<MusicCategory> getMusicByCategories(Long id){
        return musicCategoryRepository.findByMusicCategoryKeyCategoryId(id);
    }


}
