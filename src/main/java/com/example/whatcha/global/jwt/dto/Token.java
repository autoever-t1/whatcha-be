package com.example.whatcha.global.jwt.dto;

import com.example.whatcha.global.jwt.constant.TokenType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class Token {
    private final TokenType tokenType;
    private final String token;
}
