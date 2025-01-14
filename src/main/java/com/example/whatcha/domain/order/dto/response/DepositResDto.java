package com.example.whatcha.domain.order.dto.response;

import com.example.whatcha.domain.order.domain.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DepositResDto {
    private Long orderId;
    private String vhclRegNo;
    private LocalDate registrationDate;
    private String modelName;
    private int deposit;
    private int remainingAmount;

//    public static DepositResDto toDto(UsedCar usedCar, Order order) {
//        return DepositResDto.builder()
//                .vhclRegNo()
//                .registrationDate()
//                .modelName()
//                .deposit(order.getDeposit())
//                .remainingAmount(order.getRemainingAmount())
//                .build();
//    }

}
