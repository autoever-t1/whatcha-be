package com.example.whatcha.domain.usedCar.service;

import com.example.whatcha.domain.interest.dao.LikedCarRepository;
import com.example.whatcha.domain.usedCar.dao.UsedCarImageRepository;
import com.example.whatcha.domain.usedCar.dao.UsedCarRepository;
import com.example.whatcha.domain.usedCar.dao.UsedCarSpecification;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import com.example.whatcha.domain.usedCar.domain.UsedCarImage;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarDetailResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarImageResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarListResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarOrderInfoResDto;
import com.example.whatcha.domain.usedCar.exception.UsedCarNotFoundException;
import com.example.whatcha.domain.user.dao.UserRepository;
import com.example.whatcha.domain.user.domain.User;
import com.example.whatcha.domain.user.exception.UserNotFoundException;
import com.example.whatcha.global.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private final LikedCarRepository likedCarRepository;
    private final UsedCarImageRepository usedCarImageRepository;
    private final SecurityUtils securityUtils;

    private User getLoginUser() {
        String loginUserEmail = securityUtils.getLoginUserEmail();
        return userRepository.findByEmail(loginUserEmail)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));
    }

    @Override
    public UsedCarDetailResDto findOneUsedCar(Long usedCarId) {
        User loginUser = getLoginUser();
        UsedCar usedCar = usedCarRepository.findById(usedCarId)
                .orElseThrow(() -> new UsedCarNotFoundException(USED_CAR_NOT_FOUND.getMessage()));
        return UsedCarDetailResDto.entityToResDto(usedCar, loginUser, likedCarRepository);
    }

    @Override
    public Page<UsedCarListResDto> findAllUsedCar(int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        User loginUser = getLoginUser();
        return usedCarRepository.findAll(pageable)
                .map(usedCar -> UsedCarListResDto.entityToDto(usedCar, loginUser, likedCarRepository));
    }

    @Override
    public Page<UsedCarListResDto> searchUsedCar(String keyword, int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        User loginUser = getLoginUser();
        return usedCarRepository.findByModelNameContainingIgnoreCaseOrVhclRegNoContainingIgnoreCase(keyword, keyword, pageable)
                .map(usedCar -> UsedCarListResDto.entityToDto(usedCar, loginUser, likedCarRepository));
    }

    @Override
    public Page<UsedCarListResDto> filterUsedCars(
            List<Long> colorIds, List<String> modelTypes, List<String> modelNames,
            Integer mileageMin, Integer mileageMax, Integer yearMin, Integer yearMax,
            List<String> fuelTypes, Boolean hasNavigation, Boolean hasHiPass,
            Boolean hasHeatedSteeringWheel, Boolean hasHeatedSeats, Boolean hasVentilatedSeats,
            Boolean hasPowerSeats, Boolean hasLeatherSeats, Boolean hasPowerTrunk,
            Boolean hasSunroof, Boolean hasHUD, Boolean hasSurroundViewMonitor, Boolean hasRearMonitor,
            Boolean hasBlindSpotWarning, Boolean hasLaneDepartureWarning,
            Boolean hasSmartCruiseControl, Boolean hasFrontParkingWarning,
            Integer priceMin, Integer priceMax, int page) {

        Pageable pageable = PageRequest.of(page, 10);

        User loginUser = getLoginUser();

        // Specification 생성
        Specification<UsedCar> spec = usedCarSpecification.buildSpecification(
                colorIds, modelTypes, modelNames, mileageMin, mileageMax, yearMin, yearMax,
                fuelTypes, hasNavigation, hasHiPass, hasHeatedSteeringWheel,
                hasHeatedSeats, hasVentilatedSeats, hasPowerSeats, hasLeatherSeats, hasPowerTrunk,
                hasSunroof, hasHUD, hasSurroundViewMonitor, hasRearMonitor, hasBlindSpotWarning,
                hasLaneDepartureWarning, hasSmartCruiseControl, hasFrontParkingWarning, priceMin, priceMax);

        // DB에서 필터링 및 페이지네이션 수행
        Page<UsedCar> filteredCars = usedCarRepository.findAll(Specification.where(spec), pageable);

        List<UsedCarListResDto> resultDtoList = filteredCars.getContent().stream()
                .map(usedCar -> UsedCarListResDto.entityToDto(usedCar, loginUser, likedCarRepository))
                .collect(Collectors.toList());

        return new PageImpl<>(resultDtoList, pageable, filteredCars.getTotalElements());
    }


    @Override
    public UsedCarOrderInfoResDto findOneUsedCarOrderInfo(Long usedCarId) {
        UsedCar usedCar = usedCarRepository.findById(usedCarId)
                .orElseThrow(() -> new UsedCarNotFoundException(USED_CAR_NOT_FOUND.getMessage()));

        return UsedCarOrderInfoResDto.builder()
                .modelName(usedCar.getModelName())
                .registrationDate(usedCar.getRegistrationDate())
                .mileage(usedCar.getMileage())
                .price(usedCar.getPrice())
                .build();
    }


    @Override
    public List<UsedCarImageResDto> findAllImageByUsedCar(Long usedCarId) {
        List<UsedCarImage> usedCarImages = usedCarImageRepository.findByUsedCar_UsedCarId(usedCarId);

        return usedCarImages.stream()
                .map(UsedCarImageResDto::entityToResDto)
                .collect(Collectors.toList());
    }
}
