package com.example.whatcha.domain.usedCar.domain;

import com.example.whatcha.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "used_car")
public class UsedCar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usedCarId;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    private Long branchStoreId;

    private String registrationDate;

    @Column(nullable = false, unique = true)
    private String vhclRegNo;

    @Column(nullable = false)
    private String modelName;

    private String modelType;

    private String fuelType;

    @Column(nullable = false)
    private Integer mileage;

    @Column(nullable = false)
    private String exteriorColor;

    @Column(nullable = false)
    private String interiorColor;

    @Column(nullable = false)
    private Double price;

    private String status;

    @Column(nullable = false)
    private Integer year;

    private Double engineCapacity;

    private Integer passengerCapacity;

    private String driveType;

    private String transmission;

}
