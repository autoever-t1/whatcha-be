package com.example.whatcha.domain.coupon.dto.request;

import com.example.whatcha.domain.coupon.domain.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CouponReqDto {
    private String couponName;

    private int discountPercentage;

    private int discountAmount;

    private int maxDiscountAmount;

    public Coupon toEntity() {
        return Coupon.builder()
                .couponName(this.couponName)
                .discountPercentage(this.discountPercentage)
                .discountAmount(this.discountAmount)
                .maxDiscountAmount(this.maxDiscountAmount)
                .build();
    }
}
