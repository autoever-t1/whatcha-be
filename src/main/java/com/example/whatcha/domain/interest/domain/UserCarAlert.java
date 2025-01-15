package com.example.whatcha.domain.interest.domain;

import com.example.whatcha.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user_car_alerts")
public class UserCarAlert extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCarAlertId;

    private Long userId;
    private Long modelId;
    private LocalDate alertExpirationDate;
}
