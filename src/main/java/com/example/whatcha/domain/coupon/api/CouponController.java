package com.example.whatcha.domain.coupon.api;

import com.example.whatcha.domain.coupon.domain.Coupon;
import com.example.whatcha.domain.coupon.dto.response.CouponResDto;
import com.example.whatcha.domain.coupon.service.CouponService;
import com.example.whatcha.domain.order.dto.response.OrderProcessResDto;
import com.example.whatcha.global.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    //사용자 쿠폰 등록하기
    @PostMapping
    public ResponseEntity<CouponResDto> addCoupon(@RequestBody Map<String, String> couponMap) {
        try{
            //userId로 대체하기
            //accessToken에서 userEmail뽑아내기
            String email = SecurityUtils.getLoginUserEmail();

            String couponCode = couponMap.get("couponCode");
            CouponResDto response = couponService.addCoupon(couponCode,email);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CouponResDto("사용자 coupon등록 500에러 발생"));
        }
    }

    //사용자 쿠폰 리스트 조회하시 나중에 pathvariable없애기
    @GetMapping
    public ResponseEntity<?> getAllCoupons(@PageableDefault(size = 10) Pageable pageable) {
        try {
            String email = SecurityUtils.getLoginUserEmail();
            //쿠폰 리스트 조회
            Page<CouponResDto> response = couponService.getAllCoupons(email, pageable);

            // 데이터가 존재하지 않을 경우
            if (response.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("No coupons found for user email: " + email);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 발생 시 500에러 코드
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 coupon조회 500에러 발생: " + e.getMessage());
        }
    }

    // 룰렛 참여한 적 있는지 확인하는 API
    @GetMapping("/roulette")
    public ResponseEntity<Boolean> hasParticipatedInRoulette() {
        boolean exists = couponService.hasParticipatedInRoulette();
        if(exists) exists = false;
        else exists = true;
        return ResponseEntity.ok().body(exists);
    }

    // 신규 가입 쿠폰 받은 적 있는지 확인하는 API
    @GetMapping("/new-user")
    public ResponseEntity<Boolean> hasReceivedNewUserCoupon() {
        boolean exists = couponService.hasReceivedNewUserCoupon();
        if(exists) {
            exists = false;
        } else exists = true;
        return ResponseEntity.ok().body(exists);
    }
}
