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
        private int distance;              // 거리
        private int duration;              // 소요 시간
    }
}
