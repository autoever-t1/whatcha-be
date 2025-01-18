package com.example.whatcha.domain.usedCar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UsedCarOrderInfoResDto {
    private String modelName;

    private String registrationDate;

    private String mileage;

    private Integer price;
}
