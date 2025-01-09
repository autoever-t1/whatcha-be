package com.example.whatcha.domain.user.exception;

public class InvalidSignUpException extends RuntimeException {
    public InvalidSignUpException(String message) {
        super(message);
    }
}
