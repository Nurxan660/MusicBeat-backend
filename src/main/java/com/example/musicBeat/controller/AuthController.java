package com.example.musicBeat.controller;



import com.example.musicBeat.dto.*;
import com.example.musicBeat.service.AuthService;
import com.example.musicBeat.service.EmailVerificationTokenService;
import com.example.musicBeat.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    EmailVerificationTokenService emailVerificationTokenService;
    @Autowired
    private RefreshTokenService refreshTokenService;



    @PostMapping("/signup")
    public ResponseEntity signUp( @RequestBody RegistrationRequest req){

                RegistrationResponse registrationResponse=authService.registration(req);
                return ResponseEntity.ok(registrationResponse);

    }

    @PostMapping(value = "/signin")
    public ResponseEntity signIn(@RequestBody LoginRequest loginRequest){

            LoginResponse loginResponse = authService.signIn(loginRequest);
            return ResponseEntity.ok(loginResponse);

    }
    @DeleteMapping(value = "/logout")
    public ResponseEntity logout(@RequestParam Long userId){

            authService.logout(userId);
            return ResponseEntity.ok("Successfully logout");
    }



    @PostMapping(value = "/refreshToken")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){

            TokenRefreshResponse tokenRefreshResponse=refreshTokenService.checkExpiration(refreshTokenRequest.getRefreshToken());
            return ResponseEntity.ok(tokenRefreshResponse);

    }




    @PostMapping("/restorePassword")
    public ResponseEntity restorePassword(@RequestBody RestorePasswordRequest restorePasswordRequest){

            authService.restorePassword(restorePasswordRequest.getEmail());
            return ResponseEntity.ok("A link has been sent to your email");


    }

    @GetMapping("/confirmToken")
    public ResponseEntity confirmToken(@RequestParam String token) {
        emailVerificationTokenService.confirmTokenAndChangePassword(token);
        return ResponseEntity.ok("New password sended to your email");
    }









}
