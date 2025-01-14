package com.example.whatcha.domain.interest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedCarResponseDto {
    private Long usedCarId;
    private String thumbnailUrl;
    private String modelName;
    private LocalDate registrationDate;
    private int mileage;
    private String vhclRegNo;
    private int price;
    private int likeCount;
}
