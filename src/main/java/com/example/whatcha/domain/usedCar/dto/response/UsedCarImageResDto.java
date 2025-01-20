package com.example.whatcha.domain.usedCar.dto.response;

import com.example.whatcha.domain.usedCar.domain.UsedCarImage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsedCarImageResDto {
    private final String image;

    public static UsedCarImageResDto entityToResDto(UsedCarImage usedCarImage) {
        return UsedCarImageResDto.builder()
                .image(usedCarImage.getImage())
                .build();
    }
}
