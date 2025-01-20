package com.example.whatcha.domain.coupon.service;

import com.example.whatcha.domain.coupon.dao.CouponRepository;
import com.example.whatcha.domain.coupon.dao.UserCouponsRepository;
import com.example.whatcha.domain.coupon.domain.Coupon;
import com.example.whatcha.domain.coupon.domain.UserCoupons;
import com.example.whatcha.domain.coupon.dto.response.CouponResDto;
import com.example.whatcha.domain.coupon.exception.CouponAlreadyAppliedException;
import com.example.whatcha.domain.coupon.exception.CouponNotFoundException;
import com.example.whatcha.domain.coupon.exception.UserCouponsNotFoundException;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.engine.transaction.internal.jta.JtaStatusHelper.isActive;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final UserCouponsRepository userCouponsRepository;
    private final UserRepository userRepository;

    @Override
    public CouponResDto addCoupon(String couponCode, String email) {
        // DB에서 couponCode로 coupon 객체 찾기
        Coupon coupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new UserCouponsNotFoundException("Coupon not found: " + couponCode));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        // 이미 해당 쿠폰이 존재하는지 확인
        Optional<UserCoupons> existingCoupon = userCouponsRepository.findByUserAndCoupon(user, coupon);
        if (existingCoupon.isPresent()) {
            // 이미 쿠폰이 존재하면 에러 메시지 반환
            throw new CouponAlreadyAppliedException("해당 쿠폰 이미 존재!!");
        }

        // 쿠폰 등록일로부터 7일 후까지
        LocalDate newDate = LocalDate.now().plusDays(7);

        // 새로운 UserCoupons 객체 생성
        UserCoupons userCoupons = UserCoupons.builder()
                .user(user)
                .coupon(coupon)
                .expiryDate(newDate)
                .isActive(true)
                .build();

        // userCoupon 저장
        userCouponsRepository.save(userCoupons);

        // DB에서 couponId로 userCoupons 객체 찾기
        UserCoupons response = userCouponsRepository.findByCouponCouponId(coupon.getCouponId())
                .orElseThrow(() -> new CouponNotFoundException("UserCoupons not found: " + coupon.getCouponId()));

        return CouponResDto.builder()
                .userCouponId(response.getUserCouponId())
                .couponName(coupon.getCouponName())
                .discountPercentage(coupon.getDiscountPercentage())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .expiryDate(response.getExpiryDate())
                .build();
    }



    @Override
    public Page<CouponResDto> getAllCoupons(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
        Long userId = user.getUserId();

        // userId로 UserCoupons 페이지 조회
        Page<UserCoupons> userCouponsPage = userCouponsRepository.findAllByUserUserId(userId, pageable);

        // 조회된 데이터가 없으면 빈 Page 반환
        if (userCouponsPage.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<CouponResDto> couponResDtoList = userCouponsPage.stream()
                .map(userCoupon -> {
                    Coupon coupon = couponRepository.findById(userCoupon.getCoupon().getCouponId())
                            .orElseThrow(() -> new CouponNotFoundException("Coupon not found: " + userCoupon.getCoupon().getCouponId()));

                    UserCoupons userCoupons = userCouponsRepository.findByCouponCouponId(coupon.getCouponId())
                            .orElseThrow(() -> new CouponNotFoundException("UserCoupon not found: " + userCoupon.getCoupon().getCouponId()));

                    // 쿠폰 없거나 사용한 쿠폰일 때
                    if (coupon == null || !userCoupons.getIsActive()) {
                        return null;
                    }

                    return CouponResDto.builder()
                            .userCouponId(userCoupon.getUserCouponId())
                            .couponName(coupon.getCouponName())
                            .discountPercentage(coupon.getDiscountPercentage())
                            .maxDiscountAmount(coupon.getMaxDiscountAmount())
                            .expiryDate(userCoupon.getExpiryDate())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        // DTO 리스트가 비어 있으면 빈 Page 반환
        if (couponResDtoList.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        return new PageImpl<>(couponResDtoList, pageable, couponResDtoList.size());
    }
}
