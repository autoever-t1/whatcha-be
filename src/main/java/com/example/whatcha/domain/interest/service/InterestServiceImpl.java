package com.example.whatcha.domain.interest.service;

import com.example.whatcha.domain.interest.dao.LikedCarRepository;
import com.example.whatcha.domain.interest.dto.LikedCarResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

    private LikedCarRepository likedCarRepository;

//    @Override
//    public Page<LikedCarResponseDto> getLikedCarList(Pageable pageable) {
//        return likedCarRepository.findByUserId(userId, pageable)
//                .map(likedCar -> LikedCarResponseDto.builder()
//                        .usedCarId(likedCar.getUsedCar().getId())
//                        .thumbnailUrl(likedCar.getUsedCar().getThumbnailUrl())
//                        .modelName(likedCar.getUsedCar().getModelName())
//                        .registrationDate(likedCar.getUsedCar().getRegistrationDate())
//                        .mileage(likedCar.getUsedCar().getMileage())
//                        .vhclRegNo(likedCar.getUsedCar().getVhclRegNo())
//                        .price(likedCar.getUsedCar().getPrice())
//                        .likeCount((int) likedCar.getUsedCar().getLikes().stream()
//                                .filter(LikedCar::isLiked).count())
//                        .build());
//    }

    @Override
    public boolean toggleLike() {
        return false;
    }
}
