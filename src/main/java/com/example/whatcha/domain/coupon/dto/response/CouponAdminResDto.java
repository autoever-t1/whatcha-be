package com.example.whatcha.domain.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CouponAdminResDto {
    private Long couponId;

    private String couponCode;

    private String couponName;

    private int discountPercentage;

    private int discountAmount;

    private int maxDiscountAmount;

    //쿠폰 발행일 생각해보기
}
