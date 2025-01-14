package com.example.whatcha.domain.coupon.service;

import com.example.whatcha.domain.coupon.dto.response.CouponResDto;

import java.util.List;

public interface CouponService {

    //사용자 쿠폰 등록하기
    CouponResDto addCoupon(String couponCode, Long userId);

    //사용자 쿠폰 리스트 확인하기
    List<CouponResDto> getAllCoupons(Long userId);

}
