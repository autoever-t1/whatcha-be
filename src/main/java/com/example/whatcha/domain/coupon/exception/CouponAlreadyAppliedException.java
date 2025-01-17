package com.example.whatcha.domain.coupon.exception;

public class CouponAlreadyAppliedException extends RuntimeException {
    public CouponAlreadyAppliedException(String message) {
        super(message);
    }
}
