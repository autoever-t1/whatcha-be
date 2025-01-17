package com.example.whatcha.domain.usedCar.service;

import com.example.whatcha.domain.usedCar.dto.response.UsedCarDetailResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarListResDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsedCarService {

    UsedCarDetailResDto findOneUsedCar(Long usedCarId);

    Page<UsedCarListResDto> findAllUsedCar(int page);

    Page<UsedCarListResDto> searchUsedCar(String keyword, int page);

    Page<UsedCarListResDto> filterUsedCars(List<Long> colorIds, List<String> modelTypes, List<String> modelNames,
                                           Integer mileageMin, Integer mileageMax,
                                           Integer yearMin, Integer yearMax, List<String> fuelTypes,
                                           Boolean hasNavigation, Boolean hasHiPass, Boolean hasHeatedSteeringWheel,
                                           Boolean hasHeatedSeats, Boolean hasVentilatedSeats, Boolean hasPowerSeats,
                                           Boolean hasLeatherSeats, Boolean hasPowerTrunk, Boolean hasSunroof,
                                           Boolean hasHUD, Boolean hasSurroundViewMonitor, Boolean hasRearMonitor,
                                           Boolean hasBlindSpotWarning, Boolean hasLaneDepartureWarning,
                                           Boolean hasSmartCruiseControl, Boolean hasFrontParkingWarning,
                                           int page);
}
