package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.dao.RefreshTokenRedisRepository;
import com.example.whatcha.domain.user.domain.RefreshToken;
import com.example.whatcha.domain.user.dto.response.TokenInfo;
import com.example.whatcha.global.exception.NotFoundException;
import com.example.whatcha.global.exception.TokenException;
import com.example.whatcha.global.jwt.JwtTokenProvider;
import com.example.whatcha.global.jwt.constant.JwtHeaderUtil;
import com.example.whatcha.global.jwt.exception.ExpiredTokenException;
import com.example.whatcha.global.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.example.whatcha.domain.user.constant.UserExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRedisServiceImpl implements UserRedisService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtils securityUtils;

    @Value("${jwt.refresh-expired-in}")
    private long REFRESH_TOKEN_EXPIRED_IN;

    /**
     * Bearer 떼고 Access Token 가져옴
     */
    private String parseAccessToken(String accessToken) {
        log.info("[Access Token 파싱] Access Token에서 'Bearer' 제거");

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }

        // "Bearer "가 없으면 그대로 반환
        return accessToken;
    }


    /**
     * Access Token 재발급
     */
    @Override
    public TokenInfo reissueToken(String accessToken) {

        String refreshToken = findRefreshToken(securityUtils.getLoginUserEmail()).getRefreshToken();
        String parsedAccessToken = parseAccessToken(accessToken);

        String email = jwtTokenProvider.getUsernameFromExpiredToken(parsedAccessToken);

        validateRefreshToken(refreshToken, email);
        log.info("[토큰 재발급] Refresh Token 유효성 검사 완료.");

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        TokenInfo newTokenInfo = jwtTokenProvider.generateToken(authentication);

        addRefreshToken(email, newTokenInfo.getRefreshToken());

        return newTokenInfo;
    }

    /**
     * Refresh Token 검증
     */
    @Override
    public void validateRefreshToken(String refreshToken, String email) {

        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            log.error("[Refresh Token 검증] Refresh Token 만료됨.");
            throw new ExpiredTokenException(REFRESH_TOKEN_EXPIRED.getMessage());
        }

        String storedRefreshToken = findRefreshToken(email).getRefreshToken();
        log.info("[Refresh Token 검증] Redis에 저장된 Refresh Token과 비교.");

        if (!storedRefreshToken.equals(refreshToken)) {
            log.error("[Refresh Token 검증] 토큰 불일치. 저장된 토큰과 일치하지 않음.");
            throw new TokenException(TOKEN_MISMATCH.getMessage());
        }
    }

    /**
     * Redis에서 Refresh Token 찾기
     */
    @Override
    public RefreshToken findRefreshToken(String email) {
        return refreshTokenRedisRepository.findById(email)
                .orElseThrow(() -> {
                    log.error("[Redis Refresh Token 검색] 해당 이메일에 대한 Refresh Token 없음.");
                    return new NotFoundException(REFRESH_TOKEN_NOT_FOUND.getMessage());
                });
    }

    /**
     * Redis에 Refresh Token 저장 또는 업데이트
     */
    @Transactional
    @Override
    public void addRefreshToken(String email, String refreshToken) {
        RefreshToken token = RefreshToken.builder()
                .email(email)
                .refreshToken(refreshToken)
                .expiration(REFRESH_TOKEN_EXPIRED_IN)
                .build();

        refreshTokenRedisRepository.save(token);
        log.info("[Refresh Token 저장] Refresh Token 저장 완료.");
    }

    /**
     * Redis에서 Refresh Token 삭제
     */
    @Transactional
    @Override
    public void deleteRefreshToken(String email) {
        refreshTokenRedisRepository.deleteById(email);
        log.info("[Refresh Token 삭제] Refresh Token 삭제 완료.");
    }
}
