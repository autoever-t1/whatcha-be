package com.example.whatcha.domain.interest.service;

import com.example.whatcha.domain.interest.dao.LikedCarRepository;
import com.example.whatcha.domain.interest.domain.LikedCar;
import com.example.whatcha.domain.interest.dto.LikedCarResponseDto;
import com.example.whatcha.domain.usedCar.dao.UsedCarRepository;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

    private final LikedCarRepository likedCarRepository;
    private final UsedCarRepository usedCarRepository;

    @Transactional
    @Override
    public Page<LikedCarResponseDto> getLikedCarList(Long userId, Pageable pageable) {
        List<Object[]> likeCounts = likedCarRepository.findLikeCountsForUserLikedCars(userId);

        Map<Long, Integer> likeCountMap = likeCounts.stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],  // usedCarId
                        result -> Math.toIntExact((Long) result[1])   // like count
                ));

        Page<LikedCar> likedCars = likedCarRepository.findByUserId(userId, pageable);

        return likedCars.map(likedCar -> {
            UsedCar usedCar = likedCar.getUsedCar();
            Integer likeCount = likeCountMap.getOrDefault(usedCar.getUsedCarId(), 0);

            return LikedCarResponseDto.builder()
                    .usedCarId(usedCar.getUsedCarId())
                    .modelName(usedCar.getModelName())
                    .registrationDate(usedCar.getRegistrationDate())
                    .mileage(usedCar.getMileage())
                    .vhclRegNo(usedCar.getVhclRegNo())
                    .price(usedCar.getPrice())
                    .likeCount(likeCount)
                    .build();
        });
    }

    @Transactional
    @Override
    public boolean toggleLike(Long userId, Long usedCarId) {
        Optional<LikedCar> likedCarOptional = likedCarRepository.findByUserIdAndUsedCar_UsedCarId(userId, usedCarId);

        if (likedCarOptional.isPresent()) {
            LikedCar likedCar = likedCarOptional.get();
            // isLiked 값 토글
            likedCar.updateLiked(!likedCar.isLiked());
            return likedCar.isLiked(); // 변경된 상태 반환
        } else {
            UsedCar usedCar = usedCarRepository.findById(usedCarId)
                    .orElseThrow(() -> new IllegalArgumentException("UsedCar not found with id: " + usedCarId));
            // LikedCar 생성 및 저장
            LikedCar newLikedCar = LikedCar.builder()
                    .userId(userId)
                    .usedCar(usedCar)
                    .isLiked(true)
                    .build();
            likedCarRepository.save(newLikedCar);
            return true;
        }
    }
}
