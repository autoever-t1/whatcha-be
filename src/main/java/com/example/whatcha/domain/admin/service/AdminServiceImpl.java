package com.example.whatcha.domain.admin.service;

import com.example.whatcha.domain.admin.dto.response.AgeStatisticsDto;
import com.example.whatcha.domain.coupon.dao.CouponRepository;
import com.example.whatcha.domain.coupon.dao.UserCouponsRepository;
import com.example.whatcha.domain.coupon.domain.Coupon;
import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import com.example.whatcha.domain.coupon.dto.response.CouponAdminResDto;
import com.example.whatcha.domain.coupon.exception.CouponNotFoundException;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.dto.response.UserInfoResDto;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CouponRepository couponRepository;
    private final UserCouponsRepository userCouponsRepository;
    private final UserRepository userRepository;

    @Override
    public void addAdminCoupon(CouponReqDto couponReqDto) {
        String randomCouponCode = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        Coupon coupon = Coupon.builder()
                .couponCode(randomCouponCode)
                .couponName(couponReqDto.getCouponName())
                .discountPercentage(couponReqDto.getDiscountPercentage())
                .discountAmount(couponReqDto.getDiscountAmount())
                .maxDiscountAmount(couponReqDto.getMaxDiscountAmount())
                .build();
        couponRepository.save(coupon);
    }

    @Override
    public Page<CouponAdminResDto> getAllAdminCoupon(Pageable pageable) {
        return couponRepository.findAll(pageable)
                .map(coupon -> CouponAdminResDto.builder()
                        .couponId(coupon.getCouponId())
                        .couponCode(coupon.getCouponCode())
                        .couponName(coupon.getCouponName())
                        .discountPercentage(coupon.getDiscountPercentage())
                        .discountAmount(coupon.getDiscountAmount())
                        .maxDiscountAmount(coupon.getMaxDiscountAmount())
                        .build());
    }


    @Override
    public CouponAdminResDto getCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));

        return CouponAdminResDto.builder()
                .couponId(coupon.getCouponId())
                .couponCode(coupon.getCouponCode())
                .couponName(coupon.getCouponName())
                .discountPercentage(coupon.getDiscountPercentage())
                .discountAmount(coupon.getDiscountAmount())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .build();
    }

    @Override
    @Transactional
    public void deleteCoupon(Long couponId) {
        // 쿠폰 존재 여부 확인
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));

        // 해당 쿠폰과 관련된 userCoupons 삭제
        userCouponsRepository.deleteByCouponCouponId(couponId);

        // 쿠폰 삭제
        couponRepository.deleteById(couponId);
    }

    @Override
    public List<UserInfoResDto> getAllUser() {
        // User 엔티티 전체 조회
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserInfoResDto::entityToResDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserInfoResDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return UserInfoResDto.entityToResDto(user);
    }

    @Override
    public List<AgeStatisticsDto> getAgeStatistics() {
        List<User> users = userRepository.findAll();

        // 연령대별 사용자 수 계산
        Map<Integer, Long> ageStatistics = users.stream()
                .filter(user -> user.getAgeGroup() != null) // ageGroup이 null인 사용자는 제외
                .collect(Collectors.groupingBy(User::getAgeGroup, Collectors.counting()));

        // Map 데이터를 DTO 리스트로 변환
        return ageStatistics.entrySet().stream()
                .map(entry -> AgeStatisticsDto.builder()
                        .ageRange(entry.getKey()) // ageGroup 값
                        .count(entry.getValue().intValue()) // 사용자 수
                        .build())
                .collect(Collectors.toList());
    }
}
