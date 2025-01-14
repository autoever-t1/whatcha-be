package com.example.whatcha.domain.coupon.service;

import com.example.whatcha.domain.coupon.dao.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
}
