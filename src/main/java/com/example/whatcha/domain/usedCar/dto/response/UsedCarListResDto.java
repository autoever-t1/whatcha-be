package com.example.whatcha.domain.usedCar.dto.response;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsedCarListResDto {

    // 차량 정보
    private final Long usedCarId;
    private final String modelName;
    private final Integer price;
    private final String registrationDate; // 최초등록
    private final String mileage; // 주행거리
    private final String modelType; // 차종
    private final String vhclRegNo; // 차량번호
    private final String years; // 연식
    private final String status; // 구매 상태
    private final String goodsNo;
    private final String mainImage;
    private final Integer likeCount; // 찜 수

    public static UsedCarListResDto entityToDto(UsedCar usedCar) {
        return UsedCarListResDto.builder()
                .usedCarId(usedCar.getUsedCarId())
                .modelName(usedCar.getModelName())
                .price(usedCar.getPrice())
                .registrationDate(usedCar.getRegistrationDate())
                .mileage(usedCar.getMileage())
                .modelType(usedCar.getModelType())
                .vhclRegNo(usedCar.getVhclRegNo())
                .years(usedCar.getYears())
                .status(usedCar.getStatus())
                .goodsNo(usedCar.getGoodsNo())
                .mainImage(usedCar.getMainImage())
                .likeCount(usedCar.getLikeCount())
                .build();
    }
}
