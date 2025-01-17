package com.example.whatcha.domain.usedCar.constant;

public enum UsedCarExceptionMessage {
    USED_CAR_NOT_FOUND("존재하지 않는 중고차입니다.");
    private final String message;
    UsedCarExceptionMessage(String message) {this.message = message;}
    public String getMessage() {return message;}
}
