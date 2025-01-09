package com.example.whatcha.domain.user.exception;

import com.example.whatcha.global.exception.DuplicatedException;

public class EmailDuplicatedException extends DuplicatedException {

    public EmailDuplicatedException(String message) {
        super(message);
    }
}
