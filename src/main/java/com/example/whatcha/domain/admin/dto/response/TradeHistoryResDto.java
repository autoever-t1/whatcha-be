package com.example.whatcha.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TradeHistoryResDto {
    //상품번호, 차량명, 연식, 주행거리, 판매가격, 차종, 상태
    private String goodsNo;

    private String modelName;

    private String years;

    private Integer price;

    private String modelType;

    private String status;
}
