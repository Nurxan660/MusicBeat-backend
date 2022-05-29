package com.example.musicBeat.exception.handler;



import com.example.musicBeat.dto.ResponseMessage;
import com.example.musicBeat.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomAdvice {

    @ExceptionHandler({EmailAlreadyExistException.class, NicknameAlreadyExistException.class, EmailAndNicknameAlreadyExistException.class, UsernameNotFoundException.class})
    public ResponseEntity<ResponseMessage> handleRegistrationException(RuntimeException e){
        ResponseMessage exception=new ResponseMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseMessage> handleLoginException(BadCredentialsException e){
        return ResponseEntity.status(401).body(new ResponseMessage("password or nickname incorrect"));
    }

    @ExceptionHandler({TokenExpiredException.class, TokenNotFoundException.class,EmailConfirmedException.class})
    public ResponseEntity<ResponseMessage> handleRefreshTokenException(RuntimeException e){
        ResponseMessage exception=new ResponseMessage(e.getMessage());
        return ResponseEntity.status(401).body(exception);
    }

    @ExceptionHandler(PasswordMatcherException.class)
    public ResponseEntity<ResponseMessage> handlePass(PasswordMatcherException e){
        ResponseMessage exception=new ResponseMessage(e.getMessage());
        return ResponseEntity.status(400).body(exception);
    }



}
