package com.example.whatcha.domain.interest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarPreviewResponseDto {
    private Long usedCarId;
    private String thumbnailUrl;
    private String modelName;
    private String registrationDate;
    private String mileage;
    private String vhclRegNo;
    private int price;
    private Integer likeCount;
}
