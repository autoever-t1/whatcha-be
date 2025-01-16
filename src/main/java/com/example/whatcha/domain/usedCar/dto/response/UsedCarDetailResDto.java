package com.example.whatcha.domain.usedCar.dto.response;

import com.example.whatcha.domain.usedCar.domain.Option;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsedCarDetailResDto {

    // 차량 정보
    private final String modelName;
    private final Integer price;
    private final String registrationDate; //최초등록
    private final String mileage; //주행거리
    private final String fuelType; //연료
    private final Double engineCapacity; //배기량
    private final String exteriorColor; //외관컬러
    private final String interiorColor; //내장컬러
    private final String modelType; //차종
    private final Integer passengerCapacity; //승차인원
    private final String driveType; //구동방식
    private final String vhclRegNo; //차량번호
    private final String years; //연식
    private final String transmission; //변속기
    private final String status; //-> default는 구매 가능
    private final String goodsNo; //carNumber
    private final String mainImage;
    private final Integer likeCount;

    // 해당 모델 신차 가격
    private final Integer factoryPrice;

    // 신차 가격이랑 판매 가격 차이
    private final Integer comparePrice;

    // 지점 정보
    private final String branchStoreName;
    private final String location;
    private final double latitude;
    private final double longitude;
    private final String phone;

    // 옵션 정보
    private final Boolean hasNavigation;
    private final Boolean hasHiPass;
    private final Boolean hasHeatedSteeringWheel;
    private final Boolean hasHeatedSeats;
    private final Boolean hasVentilatedSeats;
    private final Boolean hasPowerSeats;
    private final Boolean hasLeatherSeats;
    private final Boolean hasPowerTrunk;
    private final Boolean hasSunroof;
    private final Boolean hasHUD;
    private final Boolean hasSurroundViewMonitor;
    private final Boolean hasRearMonitor;
    private final Boolean hasBlindSpotWarning;
    private final Boolean hasLaneDepartureWarning;
    private final Boolean hasSmartCruiseControl;
    private final Boolean hasFrontParkingWarning;

    public static UsedCarDetailResDto entityToResDto(UsedCar usedCar, Option option) {
        return UsedCarDetailResDto.builder()
                // 차량 정보
                .modelName(usedCar.getModelName())
                .price(usedCar.getPrice())
                .registrationDate(usedCar.getRegistrationDate())
                .mileage(usedCar.getMileage())
                .fuelType(usedCar.getFuelType())
                .engineCapacity(usedCar.getEngineCapacity())
                .exteriorColor(usedCar.getExteriorColor())
                .interiorColor(usedCar.getInteriorColor())
                .modelType(usedCar.getModelType())
                .passengerCapacity(usedCar.getPassengerCapacity())
                .driveType(usedCar.getDriveType())
                .vhclRegNo(usedCar.getVhclRegNo())
                .years(usedCar.getYears())
                .transmission(usedCar.getTransmission())
                .status(usedCar.getStatus())
                .goodsNo(usedCar.getGoodsNo())
                .mainImage(usedCar.getMainImage())
                .likeCount(usedCar.getLikeCount())

                // 해당 모델 신차 가격
                .factoryPrice(usedCar.getModel().getFactoryPrice())

                // 신차 가격이랑 판매 가격 차이
                .comparePrice(usedCar.getPrice() - usedCar.getModel().getFactoryPrice())

                // 지점 정보
                .branchStoreName(usedCar.getBranchStore().getBranchStoreName())
                .location(usedCar.getBranchStore().getLocation())
                .latitude(usedCar.getBranchStore().getLatitude())
                .longitude(usedCar.getBranchStore().getLongitude())
                .phone(usedCar.getBranchStore().getPhone())

                // 옵션 정보
                .hasNavigation(option.getHasNavigation())
                .hasHiPass(option.getHasHiPass())
                .hasHeatedSteeringWheel(option.getHasHeatedSteeringWheel())
                .hasHeatedSeats(option.getHasHeatedSeats())
                .hasVentilatedSeats(option.getHasVentilatedSeats())
                .hasPowerSeats(option.getHasPowerSeats())
                .hasLeatherSeats(option.getHasLeatherSeats())
                .hasPowerTrunk(option.getHasPowerTrunk())
                .hasSunroof(option.getHasSunroof())
                .hasHUD(option.getHasHUD())
                .hasSurroundViewMonitor(option.getHasSurroundViewMonitor())
                .hasRearMonitor(option.getHasRearMonitor())
                .hasBlindSpotWarning(option.getHasBlindSpotWarning())
                .hasLaneDepartureWarning(option.getHasLaneDepartureWarning())
                .hasSmartCruiseControl(option.getHasSmartCruiseControl())
                .hasFrontParkingWarning(option.getHasFrontParkingWarning())
                .build();
    }
}


