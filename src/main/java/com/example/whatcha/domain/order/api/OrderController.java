package com.example.whatcha.domain.order.api;

import com.example.whatcha.domain.admin.service.AdminService;
import com.example.whatcha.domain.order.dto.request.DepositReqDto;
import com.example.whatcha.domain.order.dto.response.*;
import com.example.whatcha.domain.order.service.OrderService;
import com.example.whatcha.global.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AdminService adminService;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderProcessResDto("프로세스 진행 현황 보기 500에러 발생"));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderResDto("주문 계약 정보 보기 500에러 발생"));
        }
    }

    //계약금 납부하기 -> order 1단계
    @PostMapping("/deposit")
    public ResponseEntity<?> payDeposit(@RequestBody DepositReqDto request) {
        try{
            String email = SecurityUtils.getLoginUserEmail();
            DepositResDto response = orderService.payDeposit(email, request.getUsedCarId(), request.getFullPayment(), request.getDeposit(), request.getUserCouponId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계약금 납부하기 오류");
        }
    }

    //잔금 결제하기 -> order 2단계
    @PostMapping("/{orderId}/fullPayment")
    public ResponseEntity<?> fullPayment(@PathVariable("orderId") Long orderId) {
        orderService.fullPayment(orderId);
        return ResponseEntity.ok().build();
    }

    //계약서 작성하기 -> order 3단계
    @PostMapping("/{orderId}/writeContract")
    public ResponseEntity<?> writeContract(@PathVariable("orderId") Long orderId) {
        orderService.writeContract(orderId);
        return ResponseEntity.ok().build();
    }

    //탁송 방법 선택 -> order 4단계
    @PutMapping("/{orderId}/deliveryService")
    public ResponseEntity<?> deliveryService(@PathVariable("orderId") Long orderId) {
        orderService.deliveryService(orderId);
        return ResponseEntity.ok().build();
    }

    // 사용자 주문서 보기
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long orderId) {
        OrderSheetResDto response = orderService.getOrderSheet(orderId);
        return ResponseEntity.ok(response);
    }

    // 사용자 주문목록 조회
    @GetMapping("/orderList")
    public ResponseEntity<List<OrderListResDto>> getAllOrders() {
        String email = SecurityUtils.getLoginUserEmail();
        List<OrderListResDto> response = orderService.getgetAllOrders(email);
        return ResponseEntity.ok(response);
    }

}
