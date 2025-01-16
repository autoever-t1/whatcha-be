package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.constant.UserExceptionMessage;
import com.example.whatcha.domain.user.dao.LogoutAccessTokenRedisRepository;
import com.example.whatcha.domain.user.dao.RefreshTokenRedisRepository;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.LogoutAccessToken;
import com.example.whatcha.domain.user.domain.RefreshToken;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.dto.request.*;
import com.example.whatcha.domain.user.dto.response.AuthenticatedResDto;
import com.example.whatcha.domain.user.dto.response.TokenInfo;
import com.example.whatcha.domain.user.dto.response.UserInfoResDto;
import com.example.whatcha.domain.user.exception.InvalidSignUpException;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import com.example.whatcha.global.exception.NotFoundException;
import com.example.whatcha.global.exception.TokenException;
import com.example.whatcha.global.jwt.JwtTokenProvider;
import com.example.whatcha.global.jwt.constant.JwtHeaderUtil;
import com.example.whatcha.global.security.util.SecurityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

import static com.example.whatcha.domain.user.constant.UserExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRedisService userRedisService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.refresh-expired-in}")
    private long REFRESH_TOKEN_EXPIRED_IN;

    /**
     * 로그인 처리 후 Access 및 Refresh Token 발급
     */
    @Transactional
    @Override
    public AuthenticatedResDto kakaoLogin(LoginReqDto loginReqDto) {
        log.info("[카카오 로그인] 카카오 로그인 정보: appToken = {}, email = {}, name = {}",
                loginReqDto.getAppToken(), loginReqDto.getEmail(), loginReqDto.getName());

        // 이미 가입된 사용자 확인
        User user = userRepository.findByEmail(loginReqDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));

        log.info("[카카오 로그인] 사용자 처리 완료: {}", user.getEmail());

        // JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().name()))
        );

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // Refresh Token을 Redis에 저장
        saveRefreshTokenInRedis(user.getEmail(), tokenInfo.getRefreshToken());

        // 응답 객체 반환
        return AuthenticatedResDto.builder()
                .userInfo(UserInfoResDto.entityToResDto(user))
                .tokenInfo(tokenInfo)
                .build();
    }


    @Override
    public AuthenticatedResDto signUp(SignUpReqDto signUpReqDto) {
        log.info("[카카오 회원가입] 신규 회원 등록 시도: 이메일 = {}, 이름 = {}", signUpReqDto.getEmail(), signUpReqDto.getName());

        // 회원가입 정보 유효성 확인
        if (userRepository.findByEmail(signUpReqDto.getEmail()) != null) {
            log.error("[회원가입] 이미 등록 된 회원.");
            throw new InvalidSignUpException(UserExceptionMessage.SIGN_UP_NOT_VALID.getMessage());
        }

        User user = userRepository.save(signUpReqDto.dtoToEntity());

        log.info("[카카오 회원가입] 저장된 사용자 ID: {}, 이메일: {}", user.getUserId(), user.getEmail());

        // JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().name()))
        );

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // Refresh Token을 Redis에 저장
        saveRefreshTokenInRedis(user.getEmail(), tokenInfo.getRefreshToken());

        // 응답 객체 반환
        return AuthenticatedResDto.builder()
                .userInfo(UserInfoResDto.entityToResDto(user))
                .tokenInfo(tokenInfo)
                .build();
    }

    private void saveRefreshTokenInRedis(String email, String refreshToken) {
        RefreshToken token = RefreshToken.builder()
                .email(email)
                .refreshToken(refreshToken)
                .expiration(REFRESH_TOKEN_EXPIRED_IN)
                .build();

        refreshTokenRedisRepository.save(token);
    }

    /**
     * 로그아웃 시 Redis에 로그아웃 처리 및 Token 무효화
     */
    @Override
    public void logout(String accessToken) {
        // 로그아웃 여부 redis에 넣어서 accessToken가 유효한지 확인
        String email = SecurityUtils.getLoginUserEmail();
        long remainMilliSeconds = jwtTokenProvider.getRemainingExpiration(accessToken);
        refreshTokenRedisRepository.deleteById(email);
        logoutAccessTokenRepository.save(LogoutAccessToken.builder()
                .email(email)
                .accessToken(accessToken)
                .expiration(remainMilliSeconds / 1000)
                .build());

        log.info("[로그아웃] 로그아웃 완료.");
    }

    /**
     * 예산 수정
     */
    @Override
    public void updateBudget(BudgetReqDto budgetReqDto) {
        String email = SecurityUtils.getLoginUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidSignUpException(UserExceptionMessage.USER_NOT_FOUND.getMessage()));

        user.updateBudget(budgetReqDto);
        userRepository.save(user);
    }

    @Override
    public void updateConsent(ConsentReqDto consentReqDto) {
        String email = SecurityUtils.getLoginUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidSignUpException(UserExceptionMessage.USER_NOT_FOUND.getMessage()));

        user.updateConsent(consentReqDto);
        userRepository.save(user);

    }

    @Override
    public void updatePreference(PreferenceModelReqDto preferenceModelReqDto) {
        String email = SecurityUtils.getLoginUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidSignUpException(UserExceptionMessage.USER_NOT_FOUND.getMessage()));

        user.updatePreferenceModel(preferenceModelReqDto);
        userRepository.save(user);
    }

    /**
     * 사용자 계정 삭제, 연관된 Token 제거
     */
    @Override
    public void deleteUser() {
        String email = SecurityUtils.getLoginUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidSignUpException(UserExceptionMessage.USER_NOT_FOUND.getMessage()));

        log.debug("[회원 탈퇴] 탈퇴 요청. 로그인 유저 : {}", user.getEmail());

        userRedisService.deleteRefreshToken(user.getEmail());
        userRepository.deleteByEmail(email);
    }

    @Override
    public UserInfoResDto findUser() {
        return null;
    }

    /**
     * Access Token에서 Bearer 타입을 제거한 순수 Token 값 반환
     */
    private String parseAccessToken(String accessToken) {
        return accessToken.substring(JwtHeaderUtil.GRANT_TYPE.getValue().length());
    }

    /**
     * 만료된 Refresh Token으로 새 Access 및 Refresh Token 발급
     */
    @Override
    public TokenInfo reissueToken(String accessToken, String refreshToken) {
        log.info("[토큰 재발급] 토큰 재발급 요청. accessToken : {}, refreshToken : {}", accessToken, refreshToken);

        String email = getEmailFromToken(refreshToken);

        checkRefreshToken(refreshToken, email);

        TokenInfo newTokenInfo = addTokens(email);

        log.info("[토큰 재발급] 토큰 재발급 성공. 새로운 accessToken : {}, 새로운 refreshToken : {}", newTokenInfo.getAccessToken(), newTokenInfo.getRefreshToken());
        return newTokenInfo;
    }

    /**
     * 새로운 Token 생성 후 Redis에 저장
     */
    private TokenInfo addTokens(String email) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
        TokenInfo newTokens = jwtTokenProvider.generateToken(authentication);

        refreshTokenRedisRepository.deleteById(email);
        refreshTokenRedisRepository.save(
                RefreshToken.builder()
                        .email(email)
                        .refreshToken(newTokens.getRefreshToken())
                        .expiration(REFRESH_TOKEN_EXPIRED_IN / 1000)
                        .build()
        );

        return newTokens;
    }

    /**
     * Refresh Token에서 이메일 추출
     */
    private String getEmailFromToken(String refreshToken) {
        try {
            return jwtTokenProvider.getUsernameFromExpiredToken(refreshToken);
        } catch (ExpiredJwtException e) {
            log.error("[토큰 재발급] 리프레시 토큰이 만료되었습니다. 재로그인 필요.");
            throw new TokenException(REFRESH_TOKEN_EXPIRED.getMessage());
        }
    }

    /**
     * Refresh Token의 유효성 확인
     */
    private void checkRefreshToken(String refreshToken, String email) {
        String storedToken = refreshTokenRedisRepository.findById(email)
                .orElseThrow(() -> new NotFoundException(REFRESH_TOKEN_NOT_FOUND.getMessage()))
                .getRefreshToken();

        if (!storedToken.equals(refreshToken)) {
            log.error("[토큰 재발급] 토큰 불일치. 재발급 불가.");
            throw new TokenException(TOKEN_MISMATCH.getMessage());
        }
    }


    /**
     * 인증 후 Access 및 Refresh Token 발급
     */
    private TokenInfo setFirstAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info("[인증 처리] 인증 성공. email : {}", email);
            return jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            log.error("[인증 실패] email : {}, error: {}", email, e.getMessage());
            throw e;
        }
    }
}
