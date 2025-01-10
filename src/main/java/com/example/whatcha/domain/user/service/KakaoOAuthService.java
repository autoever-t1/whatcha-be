package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.dto.response.AuthenticatedResDto;

public interface KakaoOAuthService {
    AuthenticatedResDto processKakaoLogin(String code);
}
