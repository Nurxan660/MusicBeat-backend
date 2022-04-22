package com.example.musicBeat.repository;

import com.example.musicBeat.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {
   Optional<RefreshToken> findByToken(String token);
   void deleteByUserId(Long id);
};
