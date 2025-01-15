package com.example.whatcha.domain.interest.api;

import com.example.whatcha.domain.interest.domain.UserCarAlert;
import com.example.whatcha.domain.interest.dto.LikedCarResponseDto;
import com.example.whatcha.domain.interest.dto.UserCarAlertRequestDto;
import com.example.whatcha.domain.interest.dto.UserCarAlertResponseDto;
import com.example.whatcha.domain.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interest")
public class InterestController {

    private final InterestService interestService;
    private Long userId = 200L;

    @GetMapping("/liked-cars")
    public ResponseEntity<Page<LikedCarResponseDto>> getLikedCarList(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<LikedCarResponseDto> items = interestService.getLikedCarList(userId, pageable);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/liked-cars/{usedCarId}")
    public ResponseEntity<?> toggleLike(@PathVariable Long usedCarId) {
        try {
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
        List<UserCarAlertResponseDto> alertedModelList = interestService.getAlertedModelList(userId);
        return ResponseEntity.ok(alertedModelList);
    }
    @DeleteMapping("/alert-cars/{modelId}")
    public ResponseEntity<Void> deleteStockNotification(@PathVariable Long modelId) {
        interestService.deleteAlertByUserAndModel(userId, modelId);
        return ResponseEntity.noContent().build();
    }
}
