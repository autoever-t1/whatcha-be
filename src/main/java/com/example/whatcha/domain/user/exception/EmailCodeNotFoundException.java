package com.example.whatcha.domain.user.exception;

import com.example.whatcha.global.exception.NotFoundException;

public class EmailCodeNotFoundException extends NotFoundException {
    public EmailCodeNotFoundException(String message) {
        super(message);
    }
}