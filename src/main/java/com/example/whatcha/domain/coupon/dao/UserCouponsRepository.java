package com.example.whatcha.domain.coupon.dao;

import com.example.whatcha.domain.coupon.domain.UserCoupons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponsRepository extends JpaRepository<UserCoupons, Long> {
    List<UserCoupons> findAllByUserUserId(Long userId);
}
