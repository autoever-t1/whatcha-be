package com.example.whatcha.domain.usedCar.service;

import com.example.whatcha.domain.usedCar.dao.UsedCarRepository;
import com.example.whatcha.domain.usedCar.dao.UsedCarSpecification;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarDetailResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarListResDto;
import com.example.whatcha.domain.usedCar.exception.UsedCarNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.whatcha.domain.usedCar.constant.UsedCarExceptionMessage.USED_CAR_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsedCarServiceImpl implements UsedCarService {

    private final UsedCarRepository usedCarRepository;
    private final UsedCarSpecification usedCarSpecification;

    @Override
    public UsedCarDetailResDto findOneUsedCar(Long usedCarId) {
        UsedCar usedCar = usedCarRepository.findById(usedCarId)
                .orElseThrow(() -> new UsedCarNotFoundException(USED_CAR_NOT_FOUND.getMessage()));

        return UsedCarDetailResDto.entityToResDto(usedCar);
    }

    @Override
    public Page<UsedCarListResDto> findAllUsedCar(int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        return usedCarRepository.findAll(pageable)
                .map(UsedCarListResDto::entityToDto);
    }

    @Override
    public Page<UsedCarListResDto> searchUsedCar(String keyword, int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);

        return usedCarRepository.searchByKeyword(keyword, pageable)
                .map(UsedCarListResDto::entityToDto);
    }

    @Override
    public Page<UsedCarListResDto> filterUsedCars(List<Long> colorIds, List<String> modelTypes, List<String> modelNames,
                                                  Integer mileageMin, Integer mileageMax,
                                                  Integer yearMin, Integer yearMax, List<String> fuelTypes,
                                                  Boolean hasNavigation, Boolean hasHiPass, Boolean hasHeatedSteeringWheel,
                                                  Boolean hasHeatedSeats, Boolean hasVentilatedSeats, Boolean hasPowerSeats,
                                                  Boolean hasLeatherSeats, Boolean hasPowerTrunk, Boolean hasSunroof,
                                                  Boolean hasHUD, Boolean hasSurroundViewMonitor, Boolean hasRearMonitor,
                                                  Boolean hasBlindSpotWarning, Boolean hasLaneDepartureWarning,
                                                  Boolean hasSmartCruiseControl, Boolean hasFrontParkingWarning,
                                                  Integer priceMin, Integer priceMax,
                                                  int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);

        log.info("============================================================");
        log.info("Model Types: {}", modelTypes);
        log.info("Fuel Types: {}", fuelTypes);
        log.info("============================================================");

        // Specification을 통해 동적으로 쿼리 생성
        var spec = usedCarSpecification.buildSpecification(
                colorIds, modelTypes, modelNames, mileageMin, mileageMax,
                yearMin, yearMax, fuelTypes, hasNavigation, hasHiPass, hasHeatedSteeringWheel,
                hasHeatedSeats, hasVentilatedSeats, hasPowerSeats, hasLeatherSeats, hasPowerTrunk,
                hasSunroof, hasHUD, hasSurroundViewMonitor, hasRearMonitor, hasBlindSpotWarning,
                hasLaneDepartureWarning, hasSmartCruiseControl, hasFrontParkingWarning,
                priceMin, priceMax);

        return usedCarRepository.findAll(spec, pageable)
                .map(UsedCarListResDto::entityToDto);
    }
}
