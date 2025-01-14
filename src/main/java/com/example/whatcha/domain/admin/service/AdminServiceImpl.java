package com.example.whatcha.domain.admin.service;

import com.example.whatcha.domain.coupon.dao.CouponRepository;
import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import com.example.whatcha.domain.coupon.dto.response.CouponAdminResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CouponRepository couponRepository;

    @Override
    public void addAdminCoupon(CouponReqDto couponReqDto) {
        couponRepository.save(couponReqDto.toEntity());
    }

    @Override
    public List<CouponAdminResDto> getAllAdminCoupon() {
        return couponRepository.findAll().stream()
                .map(coupon -> CouponAdminResDto.builder()
                        .couponId(coupon.getCouponId())
                        .couponCode(coupon.getCouponCode())
                        .couponName(coupon.getCouponName())
                        .discountPercentage(coupon.getDiscountPercentage())
                        .discountAmount(coupon.getDiscountAmount())
                        .maxDiscountAmount(coupon.getMaxDiscountAmount())
                        .build())
                .collect(Collectors.toList());
    }
}
