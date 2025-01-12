package com.example.whatcha.domain.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    @GetMapping("/{orderId}/process")
    public ResponseEntity<?> getProcessInfo(@PathVariable("orderId") Long orderId) {
        return null;
    }

    @GetMapping("/{orderId}/contract")
    public ResponseEntity<?> getContractInfo(@PathVariable("orderId") Long orderId) {
        return null;
    }

    @PostMapping("/{orderId}/deposit")
    public ResponseEntity<?> payDeposit(@PathVariable("orderId") Long orderId) {
        return null;
    }

    @PutMapping("/{orderId}/payment-type")
    public ResponseEntity<?> setPaymentType(@PathVariable("orderId") Long orderId) {
        return null;
    }

    @PostMapping("/{orderId/writeContract}")
    public ResponseEntity<?> writeContract(@PathVariable("orderId") Long orderId) {
        return null;
    }

    @GetMapping("/{orderId}/insurance-recommendations")
    public ResponseEntity<?> getInsuranceRecommendations(@PathVariable("orderId") Long orderId) {
        return null;
    }

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
