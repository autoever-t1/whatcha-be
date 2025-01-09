package com.example.whatcha.domain.user.dto.response;

import com.example.whatcha.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResDto {
    private Long userId;
    private String email;
    private String nickname;

    public static UserInfoResDto entityToResDto(User user) {
        return UserInfoResDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
