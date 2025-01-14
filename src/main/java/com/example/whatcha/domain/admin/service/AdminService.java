package com.example.whatcha.domain.admin.service;

import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import com.example.whatcha.domain.coupon.dto.response.CouponAdminResDto;

import java.util.List;

public interface AdminService {

    //관리자 쿠폰 등록하기
    void addAdminCoupon(CouponReqDto couponReqDto);

    //관리자 전체 쿠폰 조회하기
    List<CouponAdminResDto> getAllAdminCoupon();

    //관리자 쿠폰 상세 조회하기
    CouponAdminResDto getCouponById(Long couponId);

    //관리자 쿠폰 삭제하기
    void deleteCoupon(Long couponId);
}
