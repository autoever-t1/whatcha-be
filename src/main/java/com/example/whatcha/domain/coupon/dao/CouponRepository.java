package com.example.whatcha.domain.coupon.dao;

import com.example.whatcha.domain.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
