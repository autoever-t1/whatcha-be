package com.example.whatcha.domain.order.dto.request;

import lombok.Data;

@Data
public class PathInfoReqDto {
    private Double fromLng;

    private Double fromLat;

    private Double toLng;

    private Double toLat;
}
