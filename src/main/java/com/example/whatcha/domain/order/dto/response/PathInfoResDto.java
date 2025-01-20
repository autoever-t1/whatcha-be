package com.example.whatcha.domain.order.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PathInfoResDto {

    private Route route;

    @Data
    public static class Route {
        private List<TraOptimal> traoptimal;
    }

    @Data
    public static class TraOptimal {
        private List<List<Double>> path;  // 경로 좌표 배열
        private Summary summary;           // 경로 요약 정보
    }

    @Data
    public static class Summary {
        private Location start;            // 출발 위치
        private Location goal;             // 도착 위치
        private int distance;              // 거리
        private int duration;              // 소요 시간
        private String departureTime;      // 출발 시간
        private List<List<Double>> bbox;  // 경로 경계 좌표
        private int tollFare;              // 통행료
        private int taxiFare;              // 택시 요금
        private int fuelPrice;             // 연료 비용
    }

    @Data
    public static class Location {
        private List<Double> location;    // 위치 [경도, 위도]
        private Integer dir;               // 방향
    }
}
