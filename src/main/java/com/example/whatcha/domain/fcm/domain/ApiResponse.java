package com.example.whatcha.domain.fcm.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;

    public static <T> ApiResponse<T> onSuccess(String message, T data) {
        return new ApiResponse<>(true, data, message);
    }

    public static <T> ApiResponse<T> onFailure(String message) {
        return new ApiResponse<>(false, null, message);
    }
}
