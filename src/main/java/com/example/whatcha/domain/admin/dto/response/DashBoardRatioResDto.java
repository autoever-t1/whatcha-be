package com.example.whatcha.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class DashBoardRatioResDto {
    private LocalDate date;

    private Long userCount;

    private Long orderCount;

    private Long totalSales;

    private Long carStock;
}
