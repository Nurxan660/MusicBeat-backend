package com.example.musicBeat.service;

import com.example.musicBeat.dto.UserProfileResponse;
import com.example.musicBeat.entity.User;
import com.example.musicBeat.exception.PasswordMatcherException;
import com.example.musicBeat.repository.UserRepository;
import com.example.musicBeat.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserProfileResponse getProfile(Authentication authentication){
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        UserProfileResponse userProfileResponse=new UserProfileResponse(userDetails.getEmail(), userDetails.getUsername());
        return userProfileResponse;
    }

    public void changePassword(Authentication authentication,String oldPassword,String newPassword){
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();

        if(passwordEncoder.matches(oldPassword,userDetails.getPassword())){
            User user=userRepository.findByEmail(userDetails.getEmail()).orElseThrow(()->new RuntimeException("not found"));
            user.setPassword(newPassword);
        }
        else{
            throw new PasswordMatcherException("old password is incorrect");
        }
    }
}
