package com.example.whatcha.domain.interest.api;

import com.example.whatcha.domain.interest.domain.UserCarAlert;
import com.example.whatcha.domain.interest.dto.CarPreviewResponseDto;
import com.example.whatcha.domain.interest.dto.UserCarAlertRequestDto;
import com.example.whatcha.domain.interest.dto.UserCarAlertResponseDto;
import com.example.whatcha.domain.interest.service.InterestService;
import com.example.whatcha.global.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interest")
public class InterestController {

    private final InterestService interestService;
    private final SecurityUtils securityUtils;

    @GetMapping("/liked-cars")
    public ResponseEntity<Page<CarPreviewResponseDto>> getLikedCarList(
            @PageableDefault(page = 0, size = 10,
                    sort = "updatedAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long userId = securityUtils.getLoginUserId();
        Page<CarPreviewResponseDto> items = interestService.getLikedCarList(userId, pageable);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/liked-cars/{usedCarId}")
    public ResponseEntity<?> toggleLike(@PathVariable Long usedCarId) {
        try {
            Long userId = securityUtils.getLoginUserId();
            boolean isLiked = interestService.toggleLike(userId, usedCarId);
            return ResponseEntity.ok(isLiked);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
        }
    }

//    @GetMapping("/recommended-cars")

    @PostMapping("/alert-cars/{modelId}")
    public ResponseEntity<?> addUserCarAlert(@PathVariable Long modelId, @RequestBody UserCarAlertRequestDto alertRequestDto) {
        try {
            Long userId = securityUtils.getLoginUserId();
            UserCarAlert userCarAlert = interestService.addUserCarAlert(userId, modelId, alertRequestDto.getAlertExpirationDate());
            return ResponseEntity.status(HttpStatus.CREATED).body(userCarAlert);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping("/alert-cars")
    public ResponseEntity<List<UserCarAlertResponseDto>> getAlertedModelList() {
        Long userId = securityUtils.getLoginUserId();
        List<UserCarAlertResponseDto> alertedModelList = interestService.getAlertedModelList(userId);
        return ResponseEntity.ok(alertedModelList);
    }

    @DeleteMapping("/alert-cars/{modelId}")
    public ResponseEntity<Void> deleteStockNotification(@PathVariable Long modelId) {
        Long userId = securityUtils.getLoginUserId();
        interestService.deleteAlertByUserAndModel(userId, modelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/liked-cars/most-liked")
    public ResponseEntity<List<CarPreviewResponseDto>> getMostLikedCarList(@RequestParam(defaultValue = "5") int limit) {
        List<CarPreviewResponseDto> mostLikedCars = interestService.getMostLikedCarList(limit);

        return ResponseEntity.ok(mostLikedCars);
    }
}
