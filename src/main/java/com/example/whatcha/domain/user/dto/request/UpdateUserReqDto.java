package com.example.whatcha.domain.user.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserReqDto {
    private String nickname;
}
