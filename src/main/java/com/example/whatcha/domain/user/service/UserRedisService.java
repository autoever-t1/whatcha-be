package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.domain.RefreshToken;
import com.example.whatcha.domain.user.dto.response.TokenInfo;

public interface UserRedisService {

    TokenInfo reissueToken(String accessToken);
    void addRefreshToken(String email, String refreshToken);
    RefreshToken findRefreshToken(String email);
    void validateRefreshToken(String refreshToken, String email);
    void deleteRefreshToken(String email);
}
