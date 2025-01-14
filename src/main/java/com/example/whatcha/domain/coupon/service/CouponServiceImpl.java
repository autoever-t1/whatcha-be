package com.example.whatcha.domain.coupon.service;

import com.example.whatcha.domain.coupon.dao.CouponRepository;
import com.example.whatcha.domain.coupon.dao.UserCouponsRepository;
import com.example.whatcha.domain.coupon.domain.Coupon;
import com.example.whatcha.domain.coupon.domain.UserCoupons;
import com.example.whatcha.domain.coupon.dto.response.CouponResDto;
import com.example.whatcha.domain.coupon.exception.CouponNotFoundException;
import com.example.whatcha.domain.coupon.exception.UserCouponsNotFoundException;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.engine.transaction.internal.jta.JtaStatusHelper.isActive;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final UserCouponsRepository userCouponsRepository;
    private final UserRepository userRepository;

    @Override
    public CouponResDto addCoupon(String couponCode, Long userId) {
        // DB에서 couponCode로 coupon객체 찾기
        Coupon coupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new UserCouponsNotFoundException("Coupon not found: " + couponCode));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        Long couponId = coupon.getCouponId();

        LocalDate newDate = LocalDate.now().plusDays(7);

        UserCoupons userCoupons = UserCoupons.builder()
                .user(user)
                .coupon(coupon)
                .expiryDate(newDate)
                .isActive(false)
                .build();

        //userCoupon저장
        userCouponsRepository.save(userCoupons);

        // DB에서 couponId로 userCoupons객체 찾기
        UserCoupons response = userCouponsRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("UserCoupons not found: " + couponId));

        // 쿠폰 정보를 CouponResDto로 변환하여 반환
        return CouponResDto.builder()
                .couponName(coupon.getCouponName())
                .discountPercentage(coupon.getDiscountPercentage())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .expiryDate(response.getExpiryDate())
                .build();
    }

    @Override
    public List<CouponResDto> getAllCoupons(Long userId) {
        // userId로 UserCoupons 리스트 조회
        List<UserCoupons> userCouponsList = userCouponsRepository.findAllByUserUserId(userId);

        // 각 UserCoupons에서 couponId를 추출하여 Coupon 정보 조회 및 변환
        return userCouponsList.stream()
                .map(userCoupon -> {
                    // couponId로 Coupon 조회
                    Coupon coupon = couponRepository.findById(userCoupon.getCoupon().getCouponId())
                            .orElseThrow(() -> new CouponNotFoundException("Coupon not found for id: " + userCoupon.getCoupon().getCouponId()));

                    // Coupon과 UserCoupons 정보를 기반으로 CouponResDto 생성
                    return CouponResDto.builder()
                            .couponName(coupon.getCouponName())
                            .discountPercentage(coupon.getDiscountPercentage())
                            .maxDiscountAmount(coupon.getMaxDiscountAmount())
                            .expiryDate(userCoupon.getExpiryDate())
                            .build();
                })
                .collect(Collectors.toList());
    }


}
