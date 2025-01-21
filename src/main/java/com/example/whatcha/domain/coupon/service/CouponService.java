package com.example.whatcha.domain.coupon.service;

import com.example.whatcha.domain.coupon.dto.response.CouponResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponService {

    //사용자 쿠폰 등록하기
    CouponResDto addCoupon(String couponCode, String email);

    //사용자 쿠폰 리스트 확인하기
    Page<CouponResDto> getAllCoupons(String email, Pageable pageable);

    Boolean hasParticipatedInRoulette();

    Boolean hasReceivedNewUserCoupon();

}
