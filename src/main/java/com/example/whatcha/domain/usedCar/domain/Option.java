package com.example.whatcha.domain.usedCar.domain;
import com.example.whatcha.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Option extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @OneToOne
    @JoinColumn(name = "used_car_id", nullable = false)
    private UsedCar usedCar;

    @Column(nullable = false)
    private Boolean hasNavigation;

    @Column(nullable = false)
    private Boolean hasHiPass;

    @Column(nullable = false)
    private Boolean hasHeatedSteeringWheel;

    @Column(nullable = false)
    private Boolean hasHeatedSeats;

    @Column(nullable = false)
    private Boolean hasVentilatedSeats;

    @Column(nullable = false)
    private Boolean hasPowerSeats;

    @Column(nullable = false)
    private Boolean hasLeatherSeats;

    @Column(nullable = false)
    private Boolean hasPowerTrunk;

    @Column(nullable = false)
    private Boolean hasSunroof;

    @Column(nullable = false)
    private Boolean hasHUD;

    @Column(nullable = false)
    private Boolean hasSurroundViewMonitor;

    @Column(nullable = false)
    private Boolean hasRearMonitor;

    @Column(nullable = false)
    private Boolean hasBlindSpotWarning;

    @Column(nullable = false)
    private Boolean hasLaneDepartureWarning;

    @Column(nullable = false)
    private Boolean hasFrontParkingWarning;

}
