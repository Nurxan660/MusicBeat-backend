package com.example.musicBeat.controller;

import com.example.musicBeat.dto.AddMusicToPlaylistReq;
import com.example.musicBeat.dto.CreatePlaylistReq;
import com.example.musicBeat.entity.MusicPlaylist;
import com.example.musicBeat.security.UserDetailsImpl;
import com.example.musicBeat.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlayListController {

    @Autowired
    private PlayListService playListService;

    @PostMapping("/create")
    public ResponseEntity createPlaylist(@RequestBody CreatePlaylistReq createPlaylistReq, Authentication authentication){
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        playListService.createPlayList(createPlaylistReq.getName(),userDetails.getId());
        return ResponseEntity.ok("Ваш плейлист был успешно создан!");
    }

    @PostMapping("/add/music")
    public ResponseEntity addMusicToPlaylist(@RequestBody AddMusicToPlaylistReq addMusicToPlaylistReq){
        playListService.addMusicToPlaylist(addMusicToPlaylistReq.getMusicId(),addMusicToPlaylistReq.getUniqueAddress());
        return ResponseEntity.ok("Музыка была успешна добавлена в плейлист");
    }

    @GetMapping("/get/music/all")
    public ResponseEntity getMusic(@RequestParam String uniqueAddress){
        List<MusicPlaylist> res=playListService.getMusicOfPlaylist(uniqueAddress);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/get/musicByPagination")
    public ResponseEntity getMusicByPagination(@RequestParam String uniqueAddress,@RequestParam int page,@RequestParam int size){
        Page res=playListService.getMusicOfPlaylistByPagination(uniqueAddress, page, size);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/get/user")
    public ResponseEntity getUserPlaylist(@RequestParam int page,@RequestParam int size,Authentication authentication){
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        Page res=playListService.getUserPlaylist(userDetails.getId(),page,size);
        return ResponseEntity.ok(res);
    }




}
