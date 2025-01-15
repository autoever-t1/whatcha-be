package com.example.whatcha.domain.usedCar.domain;

import com.example.whatcha.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Model extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private String modelType;

    private Integer factoryPrice;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer orderCount;

    @Builder
    public Model(Long modelId, String modelName, String modelType, Integer factoryPrice, Integer orderCount) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.modelType = modelType;
        this.factoryPrice = factoryPrice;
        this.orderCount = orderCount != null ? orderCount : 0; // 기본값 설정
    }

    public void incrementOrderCount() {
        this.orderCount++;
    }
}