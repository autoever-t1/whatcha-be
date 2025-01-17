package com.example.whatcha.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AgeStatisticsDto {
    private int ageRange;
    private int count;
}
