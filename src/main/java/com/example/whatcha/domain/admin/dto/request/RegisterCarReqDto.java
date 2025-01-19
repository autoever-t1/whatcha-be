package com.example.whatcha.domain.admin.dto.request;

import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.domain.usedCar.domain.Model;
import com.example.whatcha.domain.usedCar.domain.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterCarReqDto {
    private String driveType; //구동방식

    private Double engineCapacity; //배기량

    private String exteriorColor; //외관컬러

    private String fuelType; //연료

    private String goodsNo; //carNumber

    private String interiorColor; //내장컬러

    private String mainImage;

    private String mileage; //주행거리

    private String modelName;

    private String modelType; //차종

    private Integer passengerCapacity; //승차인원

    private Integer price;

    private String registrationDate; //최초등록

    private String transmission; //변속기

    private String vhclRegNo; //차량번호

    private String years; //연식

    private Model model;

    private Option option;

    private Long branchStoreId;

    private Long colorId;
}
