package com.example.whatcha.domain.user.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginReqDto {
    private String email;
    private String name;
    private String appToken;
}
