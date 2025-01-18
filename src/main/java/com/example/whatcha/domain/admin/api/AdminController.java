package com.example.whatcha.domain.admin.api;

import com.example.whatcha.domain.admin.dto.request.RegisterCarReqDto;
import com.example.whatcha.domain.admin.dto.response.*;
import com.example.whatcha.domain.admin.service.AdminService;
import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import com.example.whatcha.domain.coupon.dto.response.CouponAdminResDto;
import com.example.whatcha.domain.order.domain.Order;
import com.example.whatcha.domain.user.dto.response.UserInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    //관리자 전체 쿠폰 조회하기
    @GetMapping("/coupon")
    public ResponseEntity<Page<CouponAdminResDto>> getAllAdminCoupon(@PageableDefault(size = 10) Pageable pageable) {
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
    public ResponseEntity<CouponAdminResDto> getCouponById(@PathVariable Long couponId) {
        CouponAdminResDto response = adminService.getCouponById(couponId);
        return ResponseEntity.ok(response);
    }

    //관리자 쿠폰 삭제하기
    @DeleteMapping("/coupon/{couponId}")
    public ResponseEntity<Void> deleteCouponById(@PathVariable Long couponId) {
        adminService.deleteCoupon(couponId);
        return ResponseEntity.ok().build();
    }

    //관리자 전체 회원보기
    @GetMapping("/user")
    public ResponseEntity<List<UserInfoResDto>> getAllUser() {
        List<UserInfoResDto> response =  adminService.getAllUser();
        return ResponseEntity.ok(response);
    }

    //관리자 회원 상세보기
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserInfoResDto> getUserById(@PathVariable Long userId) {
        UserInfoResDto response = adminService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    //관리자 회원 나이대별 통계보기
    @GetMapping("/user/statistics/age")
    public ResponseEntity<Map<String, Object>> getUserStatisticsByAge() {
        List<AgeStatisticsDto> statistics = adminService.getAgeStatistics();

        Map<String, Object> response = new HashMap<>();
        response.put("statistics", statistics);

        return ResponseEntity.ok(response);
    }

    //관리자 회원 성별 통계보기
    @GetMapping("/user/statistics/gender")
    public ResponseEntity<Map<String, Object>> getUserStatisticsByGender() {
        List<GenderStatisticsDto> statistics = adminService.getGenderStatistics();

        Map<String, Object> response = new HashMap<>();
        response.put("statistics", statistics);

        return ResponseEntity.ok(response);
    }

    //관리자 지점 정보 보기
    @GetMapping("/branch-store")
    public ResponseEntity<List<BranchStoreResDto>> getAllBranchStore() {
        List<BranchStoreResDto> response =  adminService.getAllBranchStore();
        return ResponseEntity.ok(response);
    }

    //관리자 지점 상세보기
    @GetMapping("/branch-store/{branchStoreId}")
    public ResponseEntity<List<UsedCarByBranchResDto>> getBranchStoreById(@PathVariable Long branchStoreId) {
        List<UsedCarByBranchResDto> response = adminService.getBranchStoreById(branchStoreId);
        return ResponseEntity.ok(response);
    }

    //관리자 대시보드 유저수, 판매차량, 판매금액, 차량재고
    @GetMapping("/dashBoard")
    public ResponseEntity<DashBoardResDto> getAllDashBoard() {
        DashBoardResDto response = adminService.getDashBoard();
        return ResponseEntity.ok(response);
    }

    //관리자 대시보드 최근 거래내역
    @GetMapping("/tradeHistory")
    public ResponseEntity<List<TradeHistoryResDto>> getAllTradeHistory() {
        List<TradeHistoryResDto> response = adminService.getTradeHistory();
        return ResponseEntity.ok(response);
    }

    //관리자 매물 등록하기
    @PostMapping("/registerCar")
    public ResponseEntity<?> registerCar(@RequestBody RegisterCarReqDto registerCarReqDto) {
        adminService.registerCar(registerCarReqDto);
        return ResponseEntity.ok().build();
    }

    //관리자 날짜별 계약 건수
    @GetMapping("/order/statistics/day")
    public ResponseEntity<Map<String, Object>> getOrderStatisticsByDay() {
        List<OrderStatisticsByDayResDto> statistics = adminService.getOrderStatisticsByDay();

        Map<String, Object> response = new HashMap<>();
        response.put("statistics", statistics);

        return ResponseEntity.ok(response);
    }


}

