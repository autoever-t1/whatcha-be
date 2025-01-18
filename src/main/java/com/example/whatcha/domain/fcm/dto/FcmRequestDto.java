package com.example.whatcha.domain.fcm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FcmRequestDto {
    private String deviceToken;
    private String title;
    private String body;
}
