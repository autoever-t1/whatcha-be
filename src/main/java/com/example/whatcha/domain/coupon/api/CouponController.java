package com.example.whatcha.domain.coupon.api;

import com.example.whatcha.domain.coupon.domain.Coupon;
import com.example.whatcha.domain.coupon.dto.response.CouponResDto;
import com.example.whatcha.domain.coupon.service.CouponService;
import com.example.whatcha.domain.order.dto.response.OrderProcessResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    //사용자 쿠폰 등록하기
    @PostMapping
    public ResponseEntity<CouponResDto> addCoupon(@RequestBody String couponCode) {
        try{
            //userId로 대체하기
            CouponResDto response = couponService.addCoupon(couponCode,1L);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CouponResDto("사용자 coupon등록 500에러 발생"));
        }
    }

    //사용자 쿠폰 리스트 조회하시 나중에 pathvariable없애기
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllCoupons(@PathVariable Long userId, Pageable pageable) {
        try {
            //쿠폰 리스트 조회
            Page<CouponResDto> response = couponService.getAllCoupons(userId, pageable);

            // 데이터가 존재하지 않을 경우
            if (response.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("No coupons found for user ID: " + userId);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 발생 시 500에러 코드
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 coupon등록 500에러 발생: " + e.getMessage());
        }
    }
}
