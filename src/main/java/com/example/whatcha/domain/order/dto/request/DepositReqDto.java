package com.example.whatcha.domain.order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositReqDto {
    private Long usedCarId;
    private int fullPayment;
    private int deposit;
    private Long userCouponId;
}

