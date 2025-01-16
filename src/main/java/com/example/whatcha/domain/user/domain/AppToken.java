package com.example.whatcha.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("appToken")
public class AppToken {
    @Id
    private String email;

    private String appToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private long expiration;

    @Builder
    public AppToken(String email, String appToken, long expiration) {
        this.email = email;
        this.appToken = appToken;
        this.expiration = expiration;
    }
}
