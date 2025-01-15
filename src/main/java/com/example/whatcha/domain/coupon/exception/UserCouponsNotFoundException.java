package com.example.whatcha.domain.coupon.exception;

public class UserCouponsNotFoundException extends RuntimeException {
    public UserCouponsNotFoundException(String message) {
        super(message);
    }
}
