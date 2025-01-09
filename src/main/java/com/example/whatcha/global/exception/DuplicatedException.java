package com.example.whatcha.global.exception;

public class DuplicatedException extends IllegalArgumentException {
    public DuplicatedException(String message) {
        super(message);
    }
}
