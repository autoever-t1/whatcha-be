package com.example.whatcha.domain.admin.dto.response;

import com.example.whatcha.domain.usedCar.domain.Color;
import com.example.whatcha.domain.usedCar.domain.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UsedCarByBranchResDto {
    private Long usedCarId;

    private String modelName;

    private Integer price;

    private String registrationDate;

    private String mileage;

    private String fuelType;

    private Double engineCapacity;

    private String exteriorColor;

    private String interiorColor; //내장컬러

    private String modelType; //차종

    private Integer passengerCapacity; //승차인원

    private String driveType; //구동방식

    private String vhclRegNo; //차량번호

    private String years; //연식

    private String transmission; //변속기

    private String status; //-> default는 구매 가능

    private String goodsNo; //carNumber

    private String mainImage;




}
