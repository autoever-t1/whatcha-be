package com.example.whatcha.domain.coupon.dao;

import com.example.whatcha.domain.coupon.domain.Coupon;
import com.example.whatcha.domain.coupon.domain.UserCoupons;
import com.example.whatcha.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserCouponsRepository extends JpaRepository<UserCoupons, Long> {
    Page<UserCoupons> findAllByUserUserId(Long userId, Pageable pageable);

    Optional<UserCoupons> findByCouponCouponId(Long couponId);

    Optional<UserCoupons> findByUserAndCoupon(User user, Coupon coupon);

    void deleteByCouponCouponId(Long couponId);

    boolean existsByUserAndCouponCouponCodeContainingAndIsActiveTrue(User user, String couponCode);

}

