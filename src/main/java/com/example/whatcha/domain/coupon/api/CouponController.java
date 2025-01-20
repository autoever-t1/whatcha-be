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
            String email = SecurityUtils.getLoginUserEmail();

            String couponCode = couponMap.get("couponCode");
            CouponResDto response = couponService.addCoupon(couponCode,email);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CouponResDto("사용자 coupon등록 500에러 발생"));
        }
    }

    //사용자 쿠폰 리스트 조회하기
    @GetMapping
    public ResponseEntity<?> getAllCoupons(@PageableDefault(size = 10) Pageable pageable) {
        String email = SecurityUtils.getLoginUserEmail();
        // 쿠폰 리스트 조회
        Page<CouponResDto> response = couponService.getAllCoupons(email, pageable);
        return ResponseEntity.ok(response);
    }

}
