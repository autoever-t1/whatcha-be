package com.example.whatcha.domain.usedCar.service;

import com.example.whatcha.domain.usedCar.dao.UsedCarRepository;
import com.example.whatcha.domain.usedCar.dao.UsedCarSpecification;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarDetailResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarListResDto;
import com.example.whatcha.domain.usedCar.exception.UsedCarNotFoundException;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import com.example.whatcha.global.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.whatcha.domain.usedCar.constant.UsedCarExceptionMessage.USED_CAR_NOT_FOUND;
import static com.example.whatcha.domain.user.constant.UserExceptionMessage.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsedCarServiceImpl implements UsedCarService {

    private final UsedCarRepository usedCarRepository;
    private final UsedCarSpecification usedCarSpecification;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private User getLoginUser() {
        String loginUserEmail = securityUtils.getLoginUserEmail();
        return userRepository.findByEmail(loginUserEmail)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));
    }


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

        return usedCarRepository.findByModelNameContainingIgnoreCaseOrVhclRegNoContainingIgnoreCase(keyword, keyword, pageable)
                .map(UsedCarListResDto::entityToDto);
    }

    @Override
    public Page<UsedCarListResDto> filterUsedCars(List<Long> colorIds, List<String> modelTypes, List<String> modelNames,
                                                  Integer mileageMin, Integer mileageMax, Integer yearMin, Integer yearMax,
                                                  List<String> fuelTypes, Boolean hasNavigation, Boolean hasHiPass,
                                                  Boolean hasHeatedSteeringWheel, Boolean hasHeatedSeats,
                                                  Boolean hasVentilatedSeats, Boolean hasPowerSeats, Boolean hasLeatherSeats,
                                                  Boolean hasPowerTrunk, Boolean hasSunroof, Boolean hasHUD,
                                                  Boolean hasSurroundViewMonitor, Boolean hasRearMonitor,
                                                  Boolean hasBlindSpotWarning, Boolean hasLaneDepartureWarning,
                                                  Boolean hasSmartCruiseControl, Boolean hasFrontParkingWarning,
                                                  Integer priceMin, Integer priceMax, int page) {
        // 먼저 페이지네이션 없이 필터링된 결과 가져오기
        Pageable pageable = Pageable.unpaged();

        // 1: 검색 파라미터를 기반으로 필터링할 Specification 생성
        Specification<UsedCar> spec = usedCarSpecification.buildSpecification(
                colorIds, modelTypes, mileageMin, mileageMax,
                yearMin, yearMax, fuelTypes, hasNavigation, hasHiPass, hasHeatedSteeringWheel,
                hasHeatedSeats, hasVentilatedSeats, hasPowerSeats, hasLeatherSeats, hasPowerTrunk,
                hasSunroof, hasHUD, hasSurroundViewMonitor, hasRearMonitor, hasBlindSpotWarning,
                hasLaneDepartureWarning, hasSmartCruiseControl, hasFrontParkingWarning,
                priceMin, priceMax);

        // 2: Specification을 기반으로 필터링된 전체 데이터 가져오기
        List<UsedCar> filteredCars = usedCarRepository.findAll(Specification.where(spec));

        log.info("============================================================");
        log.info("모델 이름 필터링 전 결과: ");
        filteredCars.forEach(car -> log.info("차 ID: {}, 모델 이름: {}", car.getUsedCarId(), car.getModelName()));

        // 3: 모델 이름 필터링 (필터링된 결과에서)
        if (modelNames != null && !modelNames.isEmpty()) {
            log.info("모델 이름 필터링 중... {}", modelNames);

            filteredCars = filteredCars.stream()
                    .filter(usedCar -> modelNames.stream()
                            .anyMatch(modelName -> usedCar.getModelName().toLowerCase().contains(modelName.toLowerCase())))
                    .collect(Collectors.toList());
        }

        // 4: 결과 DTO 변환
        List<UsedCarListResDto> resultDtoList = filteredCars.stream()
                .map(UsedCarListResDto::entityToDto)
                .collect(Collectors.toList());

        // 5: 페이지네이션 적용
        Pageable finalPageable = Pageable.ofSize(10).withPage(page);
        int totalElements = filteredCars.size();
        int start = (int) finalPageable.getOffset();
        int end = Math.min(start + finalPageable.getPageSize(), totalElements);
        List<UsedCarListResDto> pageContent = resultDtoList.subList(start, end);

        // 6: 페이지네이션된 결과 반환
        return new PageImpl<>(pageContent, finalPageable, totalElements);
    }

}
