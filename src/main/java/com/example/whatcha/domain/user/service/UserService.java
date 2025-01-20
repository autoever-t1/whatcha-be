package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.dto.request.*;
import com.example.whatcha.domain.user.dto.response.AuthenticatedResDto;
import com.example.whatcha.domain.user.dto.response.TokenInfo;
import com.example.whatcha.domain.user.dto.response.UserInfoResDto;

public interface UserService {
    AuthenticatedResDto signUp(SignUpReqDto signUpReqDto); //회원가입

    AuthenticatedResDto kakaoLogin(LoginReqDto loginReqDto); //로그인

    void logout(String accessToken); //로그아웃

    void updateBudget(BudgetReqDto budgetReqDto); //예산 등록 & 수정

    void updateConsent(ConsentReqDto consentReqDto); //알람 동의 등록 & 수정

    void updatePreference(PreferenceModelReqDto preferenceModelReqDto); //선호 모델 등록 & 수정

    void deleteUser(); //회원 탈퇴

    UserInfoResDto findUser(); //정보 조회

}
