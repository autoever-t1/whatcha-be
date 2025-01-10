package com.example.whatcha.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("logoutAccessToken")
public class LogoutAccessToken {

    @Id
    private String email;

    private String accessToken;

    @TimeToLive
    private long expiration;

    @Builder
    public LogoutAccessToken(String email, String accessToken, long expiration) {
        this.email = email;
        this.accessToken = accessToken;
        this.expiration = expiration;
    }
}
