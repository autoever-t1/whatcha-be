package com.example.whatcha.domain.order.service;

import com.example.whatcha.domain.order.dto.response.DepositResDto;
import com.example.whatcha.domain.order.dto.response.OrderProcessResDto;
import com.example.whatcha.domain.order.dto.response.OrderResDto;

public interface OrderService {

    //주문 프로세스 진행 현황 보기
    OrderProcessResDto getOrderProcess(Long orderId);

    //주문 계약 정보 보기
    OrderResDto getOrder(Long orderId);

    //계약금 납부하기
    DepositResDto payDeposit(Long usedCarId, int fullPayment, int deposit, Long userCouponId);
}
