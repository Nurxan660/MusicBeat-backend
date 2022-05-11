package com.example.musicBeat.controller;


import com.example.musicBeat.entity.MainCategory;
import com.example.musicBeat.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/get/categories")
    public ResponseEntity getCategories(){
        return ResponseEntity.ok(musicService.getCategories());
    }

    @GetMapping("/get/musicByCategories")
    public ResponseEntity getMusicByCategories(@RequestParam Long categoryId){
        return ResponseEntity.ok(musicService.getMusicByCategories(categoryId));
    }
    @GetMapping("/get/allByPattern")
    public ResponseEntity getAllMusicByPattern(@RequestParam String pattern,@RequestParam int page,@RequestParam int size){
        return ResponseEntity.ok(musicService.getAllMusicsByPattern(pattern,page, size));
    }

    @GetMapping("/get/all")
    public ResponseEntity getAllMusic(){
        return ResponseEntity.ok(musicService.getAllMusics());
    }
    @GetMapping("/get/mainCategories")
    public ResponseEntity getAllMainCategories(){
        List<MainCategory> mainCategoryList=musicService.getAllMainCategories();
        return ResponseEntity.ok(mainCategoryList);
    }

}
