package com.example.whatcha.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class OrderListResDto {
    private Long orderId;

    private String mainImage;

    private String modelName;

    private Integer process;

    private LocalDate orderDate;


}
