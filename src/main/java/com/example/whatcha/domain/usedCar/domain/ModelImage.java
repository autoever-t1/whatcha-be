package com.example.whatcha.domain.usedCar.domain;

import com.example.whatcha.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "model_image")
public class ModelImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelImageId;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String imageType; // 내부/외부

}
