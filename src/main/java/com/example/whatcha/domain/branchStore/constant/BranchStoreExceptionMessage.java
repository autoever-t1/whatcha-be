package com.example.whatcha.domain.branchStore.constant;

public enum BranchStoreExceptionMessage {

    BRANCH_STORE_NOT_FOUND("해당하는 지점을 찾을 수 없습니다.");
    private final String message;
    BranchStoreExceptionMessage(String message) {this.message = message;}
    public String getMessage() {return message;}
}
