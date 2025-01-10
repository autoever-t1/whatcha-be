package com.example.whatcha.domain.user.domain;

import com.example.whatcha.domain.user.constant.UserType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@RedisHash(value = "user_info")
public class UserInfo {

    @Id
    private String userId;
    private String email;
    private String nickname;
    private UserType userType;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private long expiration;
}
