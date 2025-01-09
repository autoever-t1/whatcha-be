package com.example.whatcha.domain.user.exception;

import com.example.whatcha.global.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
