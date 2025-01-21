package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.constant.UserExceptionMessage;
import com.example.whatcha.domain.user.dao.LogoutAccessTokenRedisRepository;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.LogoutAccessToken;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.dto.request.*;
import com.example.whatcha.domain.user.dto.response.AuthenticatedResDto;
import com.example.whatcha.domain.user.dto.response.TokenInfo;
import com.example.whatcha.domain.user.dto.response.UserInfoResDto;
import com.example.whatcha.domain.user.exception.InvalidSignUpException;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import com.example.whatcha.global.jwt.JwtTokenProvider;
import com.example.whatcha.global.jwt.constant.JwtHeaderUtil;
import com.example.whatcha.global.security.util.SecurityUtils;
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

import static com.example.whatcha.domain.user.constant.UserExceptionMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRedisService userRedisService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
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
        User user = userRepository.findByEmail(loginReqDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));

        TokenInfo tokenInfo = setFirstAuthentication(loginReqDto.getEmail());

        logoutAccessTokenRepository.deleteById(loginReqDto.getEmail());

        // appToken 갱신
        user.updateAppToken(loginReqDto);

        // Refresh Token을 Redis에 저장
        userRedisService.addRefreshToken(user.getEmail(), tokenInfo.getRefreshToken());

        log.info("[카카오 로그인 성공] 사용자: {}", user.getEmail());

        return AuthenticatedResDto.builder()
                .userInfo(UserInfoResDto.entityToResDto(user))
                .tokenInfo(tokenInfo)
                .build();
    }

    @Override
    public AuthenticatedResDto signUp(SignUpReqDto signUpReqDto) {

        User user = userRepository.save(signUpReqDto.dtoToEntity());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().name()))
        );

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        logoutAccessTokenRepository.deleteById(signUpReqDto.getEmail());

        userRedisService.addRefreshToken(user.getEmail(), tokenInfo.getRefreshToken());

        log.info("[카카오 회원가입 성공] 이메일: {}", user.getEmail());

        return AuthenticatedResDto.builder()
                .userInfo(UserInfoResDto.entityToResDto(user))
                .tokenInfo(tokenInfo)
                .build();
    }

    /**
     * 로그아웃 시 Redis에 로그아웃 처리 및 Token 무효화
     */
    @Override
    public void logout(String accessToken) {
        // 로그아웃 여부 redis에 넣어서 accessToken가 유효한지 확인
        String email = SecurityUtils.getLoginUserEmail();
        long remainMilliSeconds = jwtTokenProvider.getRemainingExpiration(accessToken);
        userRedisService.deleteRefreshToken(email);
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
     * 인증 후 Access 및 Refresh Token 발급
     */
    private TokenInfo setFirstAuthentication(String email) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info("[인증 성공] 사용자 인증: {}", email);
            return jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            log.error("[인증 실패] 이메일: {}, 오류: {}", email, e.getMessage());
            throw e;
        }
    }
}
