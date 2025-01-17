package com.example.whatcha.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DashBoardResDto {
    private Long userCount;

    private Long orderCount;

    private Long totalSales;

    private Long carStock;
}
