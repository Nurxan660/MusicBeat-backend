package com.example.musicBeat.service;

import com.example.musicBeat.compositeKey.MusicPlayListKey;
import com.example.musicBeat.entity.Music;
import com.example.musicBeat.entity.MusicPlaylist;
import com.example.musicBeat.entity.PlayList;
import com.example.musicBeat.entity.User;
import com.example.musicBeat.repository.MusicPlaylistRepository;
import com.example.musicBeat.repository.MusicRepository;
import com.example.musicBeat.repository.PlaylistRepository;
import com.example.musicBeat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayListService {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MusicPlaylistRepository musicPlaylistRepository;
    @Autowired
    private MusicRepository musicRepository;



    public String generateUniqueAddress(){
        return UUID.randomUUID().toString();
    }



    public void createPlayList(String name,Long id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        PlayList playList=new PlayList(name,generateUniqueAddress(),user);
        playlistRepository.save(playList);
    }

    public void addMusicToPlaylist(Long musicId,String playListUniqueAddress){
        Music music=musicRepository.findById(musicId).orElseThrow(()->new RuntimeException("music not found"));
        PlayList playList=playlistRepository.findByUniqueAddress(playListUniqueAddress).orElseThrow(()->new RuntimeException("music not found"));
        MusicPlayListKey musicPlayListKey=new MusicPlayListKey(music.getMusicId(),playList.getPlayListId());
        MusicPlaylist musicPlaylist=new MusicPlaylist(musicPlayListKey,music,playList);
        musicPlaylistRepository.save(musicPlaylist);
    }

    public List<MusicPlaylist> getMusicOfPlaylist(String  uniqueAddress){

        return musicPlaylistRepository.findByPlayListUniqueAddress(uniqueAddress);
    }

    public Page getMusicOfPlaylistByPagination(String  uniqueAddress, int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        return musicPlaylistRepository.findByPlayListUniqueAddress(uniqueAddress,pageable);
    }

    public Page getUserPlaylist(Long id,int page,int size){
        Pageable pageable=PageRequest.of(page, size);
        return playlistRepository.findByUserId(id,pageable);
    }


}
