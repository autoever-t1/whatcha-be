package com.example.whatcha.domain.order.dto.response;

import com.example.whatcha.domain.order.domain.Order;
import com.example.whatcha.domain.order.domain.OrderProcess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderProcessResDto {
    private Long orderProcessId;

    private Long orderId;

    private Boolean depositPaid;

    private Boolean contractSigned;

    private Boolean deliveryService;

    private Boolean fullyPaid;

    private Boolean deliveryCompleted;

    private String errorMessage;

    // Entity를 DTO로 변환
    public static OrderProcessResDto toDto(OrderProcess orderProcess) {
        return OrderProcessResDto.builder()
                .orderProcessId(orderProcess.getOrderProcessId())
                .orderId(orderProcess.getOrder().getOrderId()) // Order에서 orderId를 가져옴
                .depositPaid(orderProcess.getDepositPaid())
                .contractSigned(orderProcess.getContractSigned())
                .deliveryService(orderProcess.getDeliveryService())
                .fullyPaid(orderProcess.getFullyPaid())
                .deliveryCompleted(orderProcess.getDeliveryCompleted())
                .build();
    }

    public OrderProcessResDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
