package com.example.whatcha.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GenderStatisticsDto {
    private String gender;
    private int count;
}
