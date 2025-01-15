package com.example.whatcha.domain.usedCar.domain;

import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
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

    @ManyToOne
    @JoinColumn(name = "branch_store_id", nullable = false)
    private BranchStore branchStore;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private Integer price;

    private String registrationDate; //최초등록

    @Column(nullable = false)
    private String mileage; //주행거리

    private String fuelType; //연료

    private Double engineCapacity; //배기량

    @Column(nullable = false)
    private String exteriorColor; //외관컬러

    @Column(nullable = false)
    private String interiorColor; //내장컬러

    private String modelType; //차종

    private Integer passengerCapacity; //승차인원

    private String driveType; //구동방식

    @Column(nullable = false, unique = true)
    private String vhclRegNo; //차량번호

    @Column(nullable = false)
    private String years; //연식

    private String transmission; //변속기

    private String status; //-> default는 구매 가능

    private String goodsNo; //carNumber

    private String mainImage;

    @Builder
    public UsedCar(Long usedCarId, Model model, Color color, BranchStore branchStore, String registrationDate,
                   String vhclRegNo, String modelName, String modelType, String fuelType, String mileage,
                   String exteriorColor, String interiorColor, Integer price, String status, String years,
                   Double engineCapacity, Integer passengerCapacity, String driveType, String transmission,
                   String goodsNo, String mainImage) {
        this.usedCarId = usedCarId;
        this.model = model;
        this.color = color;
        this.branchStore = branchStore;
        this.registrationDate = registrationDate;
        this.vhclRegNo = vhclRegNo;
        this.modelName = modelName;
        this.modelType = modelType;
        this.fuelType = fuelType;
        this.mileage = mileage;
        this.exteriorColor = exteriorColor;
        this.interiorColor = interiorColor;
        this.price = price;
        this.status = status;
        this.years = years;
        this.engineCapacity = engineCapacity;
        this.passengerCapacity = passengerCapacity;
        this.driveType = driveType;
        this.transmission = transmission;
        this.goodsNo = goodsNo;
        this.mainImage = mainImage;
    }
}
