package com.example.whatcha.domain.order.dto.response;

import com.example.whatcha.domain.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderResDto {
    private Long orderId;

    private Long usedCarId;

    private int fullPayment;

    private int deposit;

    private String errorMessage;

    // Entity를 DTO로 변환
    public static OrderResDto toDto(Order order) {
        return OrderResDto.builder()
                .orderId(order.getOrderId())
                .usedCarId(order.getUsedCarId())
                .fullPayment(order.getFullPayment())
                .deposit(order.getDeposit())
                .build();
    }

    public OrderResDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
