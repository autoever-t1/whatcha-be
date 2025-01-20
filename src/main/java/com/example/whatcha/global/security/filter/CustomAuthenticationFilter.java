package com.example.whatcha.global.security.filter;

import com.example.whatcha.domain.user.dao.LogoutAccessTokenRedisRepository;
import com.example.whatcha.domain.user.domain.RefreshToken;
import com.example.whatcha.domain.user.dto.response.TokenInfo;
import com.example.whatcha.domain.user.service.UserRedisService;
import com.example.whatcha.global.dto.ResponseDto;
import com.example.whatcha.global.exception.DuplicatedException;
import com.example.whatcha.global.exception.TokenException;
import com.example.whatcha.global.jwt.JwtTokenProvider;
import com.example.whatcha.global.jwt.constant.JwtHeaderUtil;
import com.example.whatcha.global.jwt.constant.JwtResponseMessage;
import com.example.whatcha.global.jwt.dto.Token;
import com.example.whatcha.global.jwt.exception.MalformedHeaderException;
import com.example.whatcha.global.jwt.exception.TokenNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.example.whatcha.global.constant.ExceptionMessage.ALREADY_LOGGED_OUT;
import static com.example.whatcha.global.jwt.constant.JwtExceptionMessage.MALFORMED_HEADER;
import static com.example.whatcha.global.jwt.constant.JwtExceptionMessage.TOKEN_NOTFOUND;
import static com.example.whatcha.global.jwt.constant.TokenType.ACCESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserRedisService userRedisService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private static final String UTF_8 = "utf-8";
    @Value("${jwt.cookieName}")
    private String COOKIE_NAME;

    @Value("${jwt.refresh-expired-in}")
    private long REFRESH_TOKEN_EXPIRED_IN;

    /**
     * 허용 URL 경로 배열
     */
    private static final String[] PERMIT_URLS = {
            "/api/user/signup",
            "/api/user/login",
            "/api/user/send-email-code",
            "/api/user/check-email-code",
            "/api/user/check-login-email",
            "/api/user/reissue-token",
            "/api/whatcha/**",
            "/api/kakao",
            "/api/upload",
//            "/api/interest",
            "/api/admin/coupon",
            "/api/chat",
            "/api/crawling",
            "/api/used-car",
            "/api/used-car/search",
            "/api/used-car/filter",
            "/api/admin/user",
            "/api/admin/branch-store",
            "/api/admin/dashBoard",
            "/api/admin/tradeHistory",
            "/api/admin/order/statistics/day",
            "/api/admin/statistics/model",
            "/api/admin/pushAlarm",
            "/api/admin/registerCar",
            "/api/admin/dashBoard/ratio"
    };

    /**
     * doFilterInternal 메서드는 HTTP 요청에 포함된 Access Token을 검증하고 인증을 설정합니다.
     * 만료된 경우 Redis에 저장된 Refresh Token을 통해 새로운 Access Token을 재발급합니다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 요청 URI가 허용된 URL 경로에 해당하는지 확인하는 메서드
     */
    private boolean isPermitUrl(String requestUri) {
        for (String url : PERMIT_URLS) {
            if (requestUri.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 로그아웃 상태인지 Redis를 통해 확인합니다.
     */
    private void checkLogout(String accessToken) {
        // accessToken에서 이메일 추출
        String email = jwtTokenProvider.getUsernameFromExpiredToken(accessToken);

        // Redis에서 이메일로 로그아웃 상태 확인
        boolean isLogout = logoutAccessTokenRedisRepository.existsById(email);
        log.info("로그아웃 상태 확인 - 이메일: {}, Redis에 존재 여부: {}", email, isLogout);

        if (isLogout) {
            throw new DuplicatedException(ALREADY_LOGGED_OUT.getMessage());
        }
    }

    /**
     * Access Token이 만료된 경우 Redis에 저장된 Refresh Token을 통해 새로운 Access Token을 발급합니다.
     */
    private void handleExpiredAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = getRefreshTokenFromRedis(request);

        if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
            TokenInfo tokenInfo = reissueTokensAndSaveOnRedis(authentication);
            makeTokenInfoResponse(response, tokenInfo);
        } else {
            throw new TokenNotFoundException(TOKEN_NOTFOUND.getMessage());
        }
    }

    /**
     * Authorization 헤더에서 Access Token을 추출하여 반환합니다.
     * 헤더가 유효하지 않은 형식일 경우 예외를 발생시킵니다.
     */
    private Token resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader(JwtHeaderUtil.AUTHORIZATION.getValue());
        if (StringUtils.hasText(token) && token.startsWith(JwtHeaderUtil.GRANT_TYPE.getValue())) {
            return Token.builder()
                    .tokenType(ACCESS)
                    .token(token.substring(JwtHeaderUtil.GRANT_TYPE.getValue().length()))
                    .build();
        }
        throw new MalformedHeaderException(MALFORMED_HEADER.getMessage());
    }

    /**
     * Redis에서 저장된 Refresh Token을 가져옵니다.
     * Access Token이 만료된 경우에만 사용됩니다.
     */
    private String getRefreshTokenFromRedis(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsernameFromExpiredToken(request.getHeader(JwtHeaderUtil.AUTHORIZATION.getValue()));
        RefreshToken storedRefreshToken = userRedisService.findRefreshToken(username);

        if (storedRefreshToken == null || !StringUtils.hasText(storedRefreshToken.getRefreshToken())) {
            throw new TokenNotFoundException(TOKEN_NOTFOUND.getMessage());
        }
        return storedRefreshToken.getRefreshToken();
    }

    /**
     * TokenException 발생 시 예외 메시지를 담아 클라이언트에 응답을 반환합니다.
     */
    private void makeTokenExceptionResponse(HttpServletResponse response, TokenException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ResponseDto.create(e.getMessage())
                )
        );
    }

    /**
     * 새로운 Access Token을 발급하고 Redis에 Refresh Token을 갱신하여 저장합니다.
     */
    private TokenInfo reissueTokensAndSaveOnRedis(Authentication authentication) {
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        userRedisService.addRefreshToken(authentication.getName(), tokenInfo.getRefreshToken());
        return tokenInfo;
    }

    /**
     * 새로 발급된 Access Token을 클라이언트에게 응답합니다.
     * 메시지는 JwtResponseMessage.TOKEN_REISSUED에 정의된 메시지를 사용합니다.
     */
    private void makeTokenInfoResponse(HttpServletResponse response, TokenInfo tokenInfo) throws IOException {
        response.setStatus(HttpStatus.CREATED.value());
        response.setCharacterEncoding(UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ResponseDto.create(JwtResponseMessage.TOKEN_REISSUED.getMessage(), tokenInfo)
                )
        );
    }
}