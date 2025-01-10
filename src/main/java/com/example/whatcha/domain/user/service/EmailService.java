package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.dto.request.CheckEmailCodeReqDto;
import com.example.whatcha.domain.user.dto.response.CheckResDto;

public interface EmailService {
    void sendEmailCode(String email); //이메일 인증코드 발송

    CheckResDto checkEmailDuplicated(String email); //이메일 중복 검사

    CheckResDto checkEmailCode(CheckEmailCodeReqDto checkEmailCodeReqDto); //이메일 인증코드 유효검사
}
