package com.example.musicBeat.controller;

import com.example.musicBeat.dto.ChangePasswordRequest;
import com.example.musicBeat.entity.MusicPlaylist;
import com.example.musicBeat.repository.UserRepository;
import com.example.musicBeat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity getUserProfile(Authentication authentication){
        return ResponseEntity.ok(userService.getProfile(authentication));
    }

    @PutMapping("/change/password")
    public ResponseEntity changePassword(Authentication authentication, @RequestBody ChangePasswordRequest changePasswordRequest){
        userService.changePassword(authentication,changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        return ResponseEntity.ok("Password succesfully changed");
    }





}
