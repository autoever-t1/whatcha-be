package com.example.whatcha.domain.usedCar.api;

import com.amazonaws.Response;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarDetailResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarListResDto;
import com.example.whatcha.domain.usedCar.dto.response.UsedCarOrderInfoResDto;
import com.example.whatcha.domain.usedCar.service.UsedCarService;
import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/used-car")
@RequiredArgsConstructor
public class UsedCarController {

    private final UsedCarService usedCarService;

    // URL 디코딩을 위한 메서드
    private String decodeParam(String param) {
        return URLDecoder.decode(param, StandardCharsets.UTF_8);
    }

    // 1. 전체 중고차 리스트 조회 (페이지네이션)
    @GetMapping
    public ResponseEntity<Page<UsedCarListResDto>> findAllUsedCar(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usedCarService.findAllUsedCar(page));
    }

    // 2. 중고차 검색 (검색어 기반, 페이지네이션)
    @GetMapping("/search")
    public ResponseEntity<Page<UsedCarListResDto>> searchUsedCar(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page) {

        // URL 디코딩
        String decodedKeyword = decodeParam(keyword);

        return ResponseEntity.status(HttpStatus.OK)
                .body(usedCarService.searchUsedCar(decodedKeyword, page));
    }

    // 3. 중고차 필터링 (여러 필터 조건, 페이지네이션)
    @GetMapping("/filter")
    public ResponseEntity<Page<UsedCarListResDto>> filterUsedCar(
            @RequestParam(required = false) List<Long> colorIds,
            @RequestParam(required = false) List<String> modelTypes,
            @RequestParam(required = false) List<String> modelNames,
            @RequestParam(required = false) Integer mileageMin,
            @RequestParam(required = false) Integer mileageMax,
            @RequestParam(required = false) Integer yearMin,
            @RequestParam(required = false) Integer yearMax,
            @RequestParam(required = false) List<String> fuelTypes,
            @RequestParam(required = false) Boolean hasNavigation,
            @RequestParam(required = false) Boolean hasHiPass,
            @RequestParam(required = false) Boolean hasHeatedSteeringWheel,
            @RequestParam(required = false) Boolean hasHeatedSeats,
            @RequestParam(required = false) Boolean hasVentilatedSeats,
            @RequestParam(required = false) Boolean hasPowerSeats,
            @RequestParam(required = false) Boolean hasLeatherSeats,
            @RequestParam(required = false) Boolean hasPowerTrunk,
            @RequestParam(required = false) Boolean hasSunroof,
            @RequestParam(required = false) Boolean hasHUD,
            @RequestParam(required = false) Boolean hasSurroundViewMonitor,
            @RequestParam(required = false) Boolean hasRearMonitor,
            @RequestParam(required = false) Boolean hasBlindSpotWarning,
            @RequestParam(required = false) Boolean hasLaneDepartureWarning,
            @RequestParam(required = false) Boolean hasSmartCruiseControl,
            @RequestParam(required = false) Boolean hasFrontParkingWarning,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(defaultValue = "0") int page) {

        // 디코딩 및 빈 리스트 처리
        if (modelTypes != null) {
            modelTypes.replaceAll(this::decodeParam);
            if (modelTypes.isEmpty()) modelTypes = null;
        }
        if (modelNames != null) {
            modelNames.replaceAll(this::decodeParam);
            if (modelNames.isEmpty()) modelNames = null;
        }
        if (fuelTypes != null) {
            fuelTypes.replaceAll(this::decodeParam);
            if (fuelTypes.isEmpty()) fuelTypes = null;
        }
        if (colorIds != null && colorIds.isEmpty()) colorIds = null;

        Page<UsedCarListResDto> usedCarList = usedCarService.filterUsedCars(colorIds, modelTypes, modelNames,
                mileageMin, mileageMax, yearMin, yearMax, fuelTypes,
                hasNavigation, hasHiPass, hasHeatedSteeringWheel, hasHeatedSeats, hasVentilatedSeats, hasPowerSeats,
                hasLeatherSeats, hasPowerTrunk, hasSunroof, hasHUD, hasSurroundViewMonitor, hasRearMonitor,
                hasBlindSpotWarning, hasLaneDepartureWarning, hasSmartCruiseControl, hasFrontParkingWarning,
                priceMin, priceMax, page);

        return ResponseEntity.status(HttpStatus.OK)
                .body(usedCarList);
    }

    // 4. 중고차 상세 조회
    @GetMapping("/detail/{usedCarId}")
    public ResponseEntity<UsedCarDetailResDto> findOneUsedCar(@PathVariable Long usedCarId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usedCarService.findOneUsedCar(usedCarId));
    }

    @GetMapping("/order/{usedCarId}")
    public ResponseEntity<UsedCarOrderInfoResDto> usedCarOrderInfo(@PathVariable Long usedCarId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usedCarService.findOneUsedCarOrderInfo(usedCarId));
    }
}
