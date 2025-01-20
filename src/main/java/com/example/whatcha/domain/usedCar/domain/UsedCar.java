package com.example.whatcha.domain.usedCar.domain;

import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.domain.interest.domain.LikedCar;
import com.example.whatcha.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "branch_store_id", nullable = false)
    private BranchStore branchStore;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "option_id", nullable = true)
    private Option option;

    @OneToMany(mappedBy = "usedCar", fetch = FetchType.LAZY)
    private List<LikedCar> likedCars;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(nullable = false)
    private Integer price;

    private String registrationDate; //최초등록

    @Column(name = "mileage", nullable = false)
    private String mileage; //주행거리

    @Column(name = "fuel_type")
    private String fuelType; //연료

    @Column(name = "engine_capacity")
    private Double engineCapacity; //배기량

    @Column(name = "exterior_color", nullable = false)
    private String exteriorColor; //외관컬러

    @Column(name = "interior_color", nullable = false)
    private String interiorColor; //내장컬러

    @Column(name = "model_type")
    private String modelType; //차종

    @Column(name = "passenger_capacity")
    private Integer passengerCapacity; //승차인원

    @Column(name = "drive_type")
    private String driveType; //구동방식

    @Column(nullable = false, unique = true)
    private String vhclRegNo; //차량번호

    @Column(name = "years", nullable = false)
    private String years; //연식

    private String transmission; //변속기

    private String status; //-> default는 구매 가능

    private String goodsNo; //carNumber

    private String mainImage;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer likeCount;

    @Builder
    public UsedCar(Long usedCarId, Model model, Color color, BranchStore branchStore, String registrationDate,
                   String vhclRegNo, String modelName, String modelType, String fuelType, String mileage,
                   String exteriorColor, String interiorColor, Integer price, String status, String years,
                   Double engineCapacity, Integer passengerCapacity, String driveType, String transmission,
                   String goodsNo, String mainImage, Integer likeCount, Option option) {
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
        this.likeCount = (likeCount != null) ? likeCount : 0;
        this.option = option;
    }

    public void incrementLikeCount() {
        this.likeCount += 1;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

}
