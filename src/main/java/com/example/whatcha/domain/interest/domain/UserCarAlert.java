package com.example.whatcha.domain.interest.domain;

import com.example.whatcha.domain.usedCar.domain.Model;
import com.example.whatcha.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user_car_alerts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "model_id"})
        }
)
public class UserCarAlert extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCarAlertId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    private LocalDate alertExpirationDate;

    public void updateAlertExpirationDate(LocalDate alertExpirationDate) {
        this.alertExpirationDate = alertExpirationDate;
    }
}
