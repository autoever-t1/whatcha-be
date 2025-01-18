package com.example.whatcha.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResDto {
    private String userType;
    private Long userId;
    private String name;
    private String gender;
}
