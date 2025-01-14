package com.example.whatcha.domain.order.api;

import com.example.whatcha.domain.order.dto.request.DepositReqDto;
import com.example.whatcha.domain.order.dto.response.DepositResDto;
import com.example.whatcha.domain.order.dto.response.OrderProcessResDto;
import com.example.whatcha.domain.order.dto.response.OrderResDto;
import com.example.whatcha.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 프로세스 진행 현황 보기
    @GetMapping("/{orderId}/process")
    public ResponseEntity<OrderProcessResDto> getProcessInfo(@PathVariable("orderId") Long orderId) {
        try {
            OrderProcessResDto response = orderService.getOrderProcess(orderId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 400 BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderProcessResDto(e.getMessage()));
        } catch (Exception e) {
            // 500 INTERNAL_SERVER_ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderProcessResDto("프로세스 진행 현황 보기 에러 발생"));
        }
    }

    // 주문 계약 정보 보기
    @GetMapping("/{orderId}/contract")
    public ResponseEntity<OrderResDto> getContractInfo(@PathVariable("orderId") Long orderId) {
        try {
            OrderResDto response = orderService.getOrder(orderId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 400 BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResDto(e.getMessage()));
        } catch (Exception e) {
            // 500 INTERNAL_SERVER_ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderResDto("주문 계약 정보 보기 에러 발생"));
        }
    }


    //계약금 납부하기
    @PostMapping("/deposit")
    public ResponseEntity<?> payDeposit(@RequestBody DepositReqDto request) {
        try{
            DepositResDto response = orderService.payDeposit(request.getUsedCarId(), request.getFullPayment(), request.getDeposit(), request.getUserCouponId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계약금 납부하기 오류");
        }
    }

    //할부 완납 선택?
    @PutMapping("/{orderId}/payment-type")
    public ResponseEntity<?> setPaymentType(@PathVariable("orderId") Long orderId) {
        return null;
    }

    //계약서 작성하기
    @PostMapping("/{orderId}/writeContract")
    public ResponseEntity<?> writeContract(@PathVariable("orderId") Long orderId) {
        return null;
    }

    //보험 관련 -> insuranceRegistered이거 status바꿔주기
    @GetMapping("/{orderId}/insurance-recommendations")
    public ResponseEntity<?> getInsuranceRecommendations(@PathVariable("orderId") Long orderId) {
        return null;
    }

    //명의이전 관련 -> ownershipTransferred status바꿔주기
    @PostMapping("/{orderId}/ownership-transfer")
    public ResponseEntity<?> setOwnershipTransfer(@PathVariable("orderId") Long orderId) {
        return null;
    }

    @PutMapping("/{orderId}/delivery-method")
    public ResponseEntity<?> setDeliveryMethod(@PathVariable("orderId") Long orderId) {
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long orderId) {
        return null;
    }
}
