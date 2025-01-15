package com.example.whatcha.domain.interest.api;

import com.example.whatcha.domain.interest.dto.LikedCarResponseDto;
import com.example.whatcha.domain.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> toggleLike(@PathVariable Long usedCarId) {
        boolean isLiked = interestService.toggleLike(userId, usedCarId);
        return ResponseEntity.ok(isLiked);
    }

//    @GetMapping("/recommended-cars")

//    @DeleteMapping("/alert-cars/{modelId}")
//    public ResponseEntity<Void> deleteStockNotification(@PathVariable Long modelId) {
//
//        return ResponseEntity.noContent().build();
//    }
}
