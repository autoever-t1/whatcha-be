package com.example.whatcha.domain.order.service;

import com.example.whatcha.domain.coupon.dao.CouponRepository;
import com.example.whatcha.domain.coupon.dao.UserCouponsRepository;
import com.example.whatcha.domain.coupon.domain.UserCoupons;
import com.example.whatcha.domain.order.dao.OrderProcessRepository;
import com.example.whatcha.domain.order.dao.OrderRepository;
import com.example.whatcha.domain.order.domain.Order;
import com.example.whatcha.domain.order.domain.OrderProcess;
import com.example.whatcha.domain.order.dto.response.DepositResDto;
import com.example.whatcha.domain.order.dto.response.OrderProcessResDto;
import com.example.whatcha.domain.order.dto.response.OrderResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProcessRepository orderProcessRepository;
    private final UserCouponsRepository userCouponsRepository;
    private final CouponRepository couponRepository;

    @Override
    public OrderProcessResDto getOrderProcess(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        //OrderProcess를 OrderProcessResDto로 변환
        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        return orderProcessResDto;
    }

    @Override
    public OrderResDto getOrder(Long orderId) {
        //orderId를 통해 Order를 조회
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for orderId: " + orderId));

        //Order를 OrderResDto로 변환
        OrderResDto orderResDto = OrderResDto.toDto(order);
        return orderResDto;
    }

    //이때 order, orderprocess save해야함
    @Override
    public DepositResDto payDeposit(Long usedCarId, int fullPayment, int deposit, Long userCouponId) {

        //해야할 일 -> 쿠폰아이디 받으면 쿠폰아이디로 쿠폰 객체 찾고 계산해서 가격 반환하기

//        UserCoupons userCoupons = userCouponsRepository.findById(userCouponId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid userCouponId: " + userCouponId));



        //진짜 유저 데이터 가져와서 넣기 & 진짜 usedCar데이터
        //usedCarId로 usedCar객체 찾기

        Order order = Order.builder()
                //.userCoupons(userCoupons)
                .usedCarId(1L)
                .userId(1L)
                .fullPayment(fullPayment)
                .deposit(deposit)
                .build();
        orderRepository.save(order);

        //orderprocess에서 depositPaid true로 바꾸고 나머지 다 false
        OrderProcess orderProcess = OrderProcess.builder()
                .order(order)
                //.userCoupons(userCoupons)
                .depositPaid(true)
                .contractSigned(false)
                .deliveryService(false)
                .fullyPaid(false)
                .deliveryCompleted(false)
                .build();

        orderProcessRepository.save(orderProcess);

        //usercoupon에서 isActive false로 바꾸고 remaining amount계산해서 넘기기

        DepositResDto depositResDto = DepositResDto.builder()
                .nickName("user객체에서 nickname찾아서 넘기기")
                .vhclRegNo("차량번호 usedCar에서 가져오기")
                .registrationDate(order.getCreatedAt())
                .modelName("차량모델 usedCar에서 가져오기")
                .deposit(deposit)
                .remainingAmount(123456789)
                .build();
        return depositResDto;
    }

    @Override
    public void fullPayment(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        orderProcessResDto.setFullyPaid(true);

        OrderProcess result = OrderProcess.builder()
                .order(orderProcess.getOrder())  // 기존 order 설정
                .userCoupons(orderProcess.getUserCoupons())  // 기존 userCoupons 설정
                .depositPaid(orderProcessResDto.getDepositPaid())
                .contractSigned(orderProcessResDto.getContractSigned())
                .fullyPaid(orderProcessResDto.getFullyPaid())
                .deliveryService(orderProcessResDto.getDeliveryService())
                .deliveryCompleted(orderProcessResDto.getDeliveryCompleted())
                .build();
        orderProcessRepository.save(result);
    }

    @Override
    public void writeContract(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        orderProcessResDto.setContractSigned(true);

        OrderProcess result = OrderProcess.builder()
                .order(orderProcess.getOrder())  // 기존 order 설정
                .userCoupons(orderProcess.getUserCoupons())  // 기존 userCoupons 설정
                .depositPaid(orderProcessResDto.getDepositPaid())
                .contractSigned(orderProcessResDto.getContractSigned())
                .fullyPaid(orderProcessResDto.getFullyPaid())
                .deliveryService(orderProcessResDto.getDeliveryService())
                .deliveryCompleted(orderProcessResDto.getDeliveryCompleted())
                .build();
        orderProcessRepository.save(result);
    }

    @Override
    public void deliveryService(Long orderId) {
        //orderId를 통해 OrderProcess를 조회
        OrderProcess orderProcess = orderProcessRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("OrderProcess not found for orderId: " + orderId));

        OrderProcessResDto orderProcessResDto = OrderProcessResDto.toDto(orderProcess);
        orderProcessResDto.setDeliveryService(true);

        OrderProcess result = OrderProcess.builder()
                .order(orderProcess.getOrder())  // 기존 order 설정
                .userCoupons(orderProcess.getUserCoupons())  // 기존 userCoupons 설정
                .depositPaid(orderProcessResDto.getDepositPaid())
                .contractSigned(orderProcessResDto.getContractSigned())
                .fullyPaid(orderProcessResDto.getFullyPaid())
                .deliveryService(orderProcessResDto.getDeliveryService())
                .deliveryCompleted(orderProcessResDto.getDeliveryCompleted())
                .build();
        orderProcessRepository.save(result);
    }

}
