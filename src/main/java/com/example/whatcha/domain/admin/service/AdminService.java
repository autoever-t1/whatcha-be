package com.example.whatcha.domain.admin.service;

import com.example.whatcha.domain.admin.dto.request.RegisterCarReqDto;
import com.example.whatcha.domain.admin.dto.response.*;
import com.example.whatcha.domain.coupon.dto.request.CouponReqDto;
import com.example.whatcha.domain.coupon.dto.response.CouponAdminResDto;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import com.example.whatcha.domain.user.dto.response.UserInfoResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    //관리자 쿠폰 등록하기
    void addAdminCoupon(CouponReqDto couponReqDto);

    //관리자 전체 쿠폰 조회하기
    Page<CouponAdminResDto> getAllAdminCoupon(Pageable pageable);

    //관리자 쿠폰 상세 조회하기
    CouponAdminResDto getCouponById(Long couponId);

    //관리자 쿠폰 삭제하기
    void deleteCoupon(Long couponId);

    //관리자 전체 회원보기
    List<UserInfoResDto> getAllUser();

    //관리자 회원 상세보기
    UserInfoResDto getUserById(Long userId);

    //관리자 연령대 통계보기
    List<AgeStatisticsDto> getAgeStatistics();

    //관리자 성별대 통계보기
    List<GenderStatisticsDto> getGenderStatistics();

    //관리자 지점 보기
    List<BranchStoreResDto> getAllBranchStore();

    //관리자 지점 별 매물보기
    List<UsedCarByBranchResDto> getBranchStoreById(Long branchStoreId);

    //관리자 대시보드
    DashBoardResDto getDashBoard();

    //관리자 거래내역 보기
    List<TradeHistoryResDto> getTradeHistory();

    //관리자 매물 등록하기
    void registerCar(RegisterCarReqDto registerCarReqDto);

    //관리자 매물 등록 후 푸시 알람 보내기
    String pushAlarm(RegisterCarReqDto registerCarReqDto);

    //관리자 날짜별 계약보기
    List<OrderStatisticsByDayResDto> getOrderStatisticsByDay();

    //관리자 차종별 판매 비율보기
    List<CarStatisticsByModelResDto> getCarStatisticsByModel();

    //관리자 대시보드 스케쥴러로 날짜와 함께 저장하기
    void dashBoardRatio();

    //관리자 대시보드 비율 확인하기
    List<DashBoardRatioResDto> getDashBoardRatio();
}
