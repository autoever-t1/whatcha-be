package com.example.whatcha.domain.admin.api;

import com.example.whatcha.domain.admin.service.AdminService;
import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import com.example.whatcha.domain.coupon.dto.response.CouponAdminResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //관리자 전체 쿠폰 조회하기
    @GetMapping("/coupon")
    public ResponseEntity<Page<CouponAdminResDto>> getAllAdminCoupon(Pageable pageable) {
        Page<CouponAdminResDto> response = adminService.getAllAdminCoupon(pageable);
        return ResponseEntity.ok(response);
    }


    //관리자 쿠폰 등록하기
    @PostMapping("/coupon")
    public ResponseEntity<Void> registerAdminCoupon(@RequestBody CouponReqDto couponReqDto) {
        adminService.addAdminCoupon(couponReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //관리자 쿠폰 상세조회하기
    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<?> getCouponById(@PathVariable Long couponId) {
        CouponAdminResDto response = adminService.getCouponById(couponId);
        return ResponseEntity.ok(response);
    }

    //관리자 쿠폰 삭제하기
    @DeleteMapping("/coupon/{couponId}")
    public ResponseEntity<Void> deleteCouponById(@PathVariable Long couponId) {
        adminService.deleteCoupon(couponId);
        return ResponseEntity.ok().build();
    }

}

