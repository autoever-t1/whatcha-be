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
@Table(name = "used_car_image")
public class UsedCarImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usedCarImageId;

    @ManyToOne
    @JoinColumn(name = "used_car_id", nullable = false)
    private UsedCar usedCar;

    @Column(nullable = false)
    private String image;

    @Builder
    public UsedCarImage(UsedCar usedCar, String image) {
        this.usedCar = usedCar;
        this.image = image;
    }

}
