package com.example.musicBeat.controller;


import com.example.musicBeat.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music")
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
}
