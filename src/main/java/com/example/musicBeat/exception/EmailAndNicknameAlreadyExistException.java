package com.example.musicBeat.exception;

public class EmailAndNicknameAlreadyExistException extends RuntimeException{
    public EmailAndNicknameAlreadyExistException(String message) {
        super(message);
    }
}
