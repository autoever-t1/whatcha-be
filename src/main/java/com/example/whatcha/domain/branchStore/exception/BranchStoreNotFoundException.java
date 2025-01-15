package com.example.whatcha.domain.branchStore.exception;

import com.example.whatcha.global.exception.NotFoundException;

public class BranchStoreNotFoundException extends NotFoundException {
    public BranchStoreNotFoundException(String message) {
        super(message);
    }
}

