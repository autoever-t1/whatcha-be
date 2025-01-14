package com.example.whatcha.domain.coupon.api;

import com.example.whatcha.domain.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    @PostMapping
    public ResponseEntity<?> addCoupon(@RequestBody String couponCode) {
        return null;
    }
}
