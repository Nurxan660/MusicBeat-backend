package com.example.musicBeat.repository;


import com.example.musicBeat.entity.EmailVerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends CrudRepository<EmailVerificationToken,Long> {

    Optional<EmailVerificationToken> findByToken(String token);
}
