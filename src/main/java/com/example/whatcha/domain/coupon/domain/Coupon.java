package com.example.whatcha.domain.coupon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "coupons")
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String couponCode;

    private String couponName;

    private int discountPercentage;

    private int discountAmount;

    private int maxDiscountAmount;

}
