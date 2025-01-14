package com.example.whatcha.domain.admin.service;

import com.example.whatcha.domain.coupon.dao.CouponRepository;
import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CouponRepository couponRepository;

    @Override
    public void addAdminCoupon(CouponReqDto couponReqDto) {
        couponRepository.save(couponReqDto.toEntity());
    }
}
