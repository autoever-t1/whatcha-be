package com.example.whatcha.domain.admin.api;

import com.example.whatcha.domain.admin.service.AdminService;
import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/order/stats/model")
    public ResponseEntity<?> getStatsByModel() {
        return null;
    }

    @GetMapping("/order/stats/age-groups")
    public ResponseEntity<?> getStatsByAge() {
        return null;
    }

    @PostMapping("/coupon")
    public ResponseEntity<Void> registerAdminCoupon(@RequestBody CouponReqDto couponReqDto) {
        adminService.addAdminCoupon(couponReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

