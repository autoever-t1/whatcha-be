package com.example.whatcha.domain.user.exception;

import com.example.whatcha.global.exception.NotFoundException;

public class UserIdNotFoundException extends NotFoundException {
  public UserIdNotFoundException(String message) {
    super(message);
  }
}
