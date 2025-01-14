package com.example.whatcha.domain.admin.service;

import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import com.example.whatcha.domain.coupon.dto.response.CouponAdminResDto;

import java.util.List;

public interface AdminService {

    //관리자 쿠폰 등록하기
    void addAdminCoupon(CouponReqDto couponReqDto);

    List<CouponAdminResDto> getAllAdminCoupon();
}
