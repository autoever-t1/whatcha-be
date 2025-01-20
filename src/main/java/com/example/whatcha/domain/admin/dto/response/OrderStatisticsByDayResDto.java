package com.example.whatcha.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class OrderStatisticsByDayResDto {
    private LocalDate date;
    private int count;
}
