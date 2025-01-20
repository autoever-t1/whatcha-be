package com.example.whatcha.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CarStatisticsByModelResDto {
    private String modelName;
    private Integer orderCount;
}
