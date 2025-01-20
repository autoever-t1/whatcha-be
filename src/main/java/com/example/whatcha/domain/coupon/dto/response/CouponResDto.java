package com.example.whatcha.domain.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CouponResDto {
    private Long userCouponId;

    private String couponName;

    private Integer discountPercentage;

    private Integer maxDiscountAmount;

    private LocalDate expiryDate;

    private String errorMessage;

    public CouponResDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
