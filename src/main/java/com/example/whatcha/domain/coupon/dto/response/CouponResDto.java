package com.example.whatcha.domain.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CouponResDto {
    private String couponName;

    private int discountPercentage;

    private int maxDiscountAmount;

    private int expiryDate;
}
