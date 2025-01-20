package com.example.whatcha.domain.usedCar.dto.response;

import com.example.whatcha.domain.interest.dao.LikedCarRepository;
import com.example.whatcha.domain.interest.domain.LikedCar;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import com.example.whatcha.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class UsedCarListResDto {

    // 차량 정보
    private final Long usedCarId;
    private final String modelName;
    private final Integer price;
    private final String registrationDate; // 최초등록
    private final String mileage; // 주행거리
    private final String modelType; // 차종
    private final String vhclRegNo; // 차량번호
    private final String years; // 연식
    private final String status; // 구매 상태
    private final String goodsNo;
    private final String mainImage;
    private final Integer likeCount; // 찜 수
    private final Boolean isLiked; // 유저 좋아요 여부

    public static UsedCarListResDto entityToDto(UsedCar usedCar, User user, LikedCarRepository likedCarRepository) {
        // 사용자가 해당 차량을 좋아요했는지 확인
        Optional<LikedCar> likedCarOptional = likedCarRepository.findByUserIdAndUsedCar_UsedCarId(user.getUserId(), usedCar.getUsedCarId());
        boolean isLiked = likedCarOptional.map(LikedCar::isLiked).orElse(false); // 값이 없으면 기본값 false

        return UsedCarListResDto.builder()
                .usedCarId(usedCar.getUsedCarId())
                .modelName(usedCar.getModelName())
                .price(usedCar.getPrice())
                .registrationDate(usedCar.getRegistrationDate())
                .mileage(usedCar.getMileage())
                .modelType(usedCar.getModelType())
                .vhclRegNo(usedCar.getVhclRegNo())
                .years(usedCar.getYears())
                .status(usedCar.getStatus())
                .goodsNo(usedCar.getGoodsNo())
                .mainImage(usedCar.getMainImage())
                .likeCount(usedCar.getLikeCount())
                .isLiked(isLiked)
                .build();
    }
}
