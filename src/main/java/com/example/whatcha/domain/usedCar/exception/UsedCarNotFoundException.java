package com.example.whatcha.domain.usedCar.exception;

import com.example.whatcha.global.exception.NotFoundException;

public class UsedCarNotFoundException extends NotFoundException {
    public UsedCarNotFoundException(String message) {
        super(message);
    }
}
