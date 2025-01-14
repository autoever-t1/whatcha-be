package com.example.whatcha.domain.admin.service;

import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;

public interface AdminService {

    //관리자 쿠폰 등록하기
    void addAdminCoupon(CouponReqDto couponReqDto);
}
