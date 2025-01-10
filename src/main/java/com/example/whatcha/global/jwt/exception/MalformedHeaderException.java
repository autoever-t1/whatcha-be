package com.example.whatcha.global.jwt.exception;

public class MalformedHeaderException extends RuntimeException {
    public MalformedHeaderException(String message) {
        super(message);
    }
}
