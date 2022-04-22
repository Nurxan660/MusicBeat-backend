package com.example.musicBeat.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TokenRefreshResponse {
    @NonNull
    private String accessToken;
    @NonNull
    private String refreshToken;
    private String type="Bearer";


}
