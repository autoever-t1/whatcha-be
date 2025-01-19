package com.example.whatcha.domain.order.service;

import com.example.whatcha.domain.order.dto.request.PathInfoReqDto;
import com.example.whatcha.domain.order.dto.response.*;

import java.util.List;

public interface OrderService {

    //주문 프로세스 진행 현황 보기
    OrderProcessResDto getOrderProcess(Long orderId);

    //주문 계약 정보 보기
    OrderResDto getOrder(Long orderId);

    //계약금 납부하기
    DepositResDto payDeposit(String email, Long usedCarId, int fullPayment, int deposit, Long userCouponId);

    //잔금 결제하기
    void fullPayment(Long orderId);

    //계약서 서명하기
    void writeContract(Long orderId);

    //수령방법 선택하기
    void deliveryService(Long orderId);

    //배송완료
    void deliveryCompleted(Long orderId);

    //주문 목록 조회하기
    List<OrderListResDto> getgetAllOrders(String email);

    //주문서 보기
    OrderSheetResDto getOrderSheet(Long orderId);

    //order naver지도 api사용
    PathInfoResDto getPathInfo(PathInfoReqDto request) throws Exception;
}
