package com.example.whatcha.domain.user.service;

import com.example.whatcha.domain.user.dto.request.*;
import com.example.whatcha.domain.user.dto.response.AuthenticatedResDto;
import com.example.whatcha.domain.user.dto.response.TokenInfo;
import com.example.whatcha.domain.user.dto.response.UserInfoResDto;

public interface UserService {
    void signUp(SignUpReqDto userInfoReqDto); //회원가입

    AuthenticatedResDto login(LoginReqDto loginReqDto); //로그인

    void logout(String accessToken); //로그아웃

    void updateBudget(UpdateBudgetReqDto updateBudgetReqDto); //예산 수정

    void deleteUser(); //회원 탈퇴

    void updatePassword(UpdatePasswordReqDto updatePasswordReqDto); //비밀번호 변경

    UserInfoResDto findUser(); //정보 조회

    TokenInfo reissueToken(String accessToken, String refreshToken); //토큰 재발급
}
