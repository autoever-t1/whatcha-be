package com.example.whatcha.domain.user.domain;

import com.example.whatcha.domain.user.constant.UserType;
import com.example.whatcha.domain.user.dto.request.UpdatePreferenceModelReqDto;
import com.example.whatcha.domain.user.dto.request.UpdateBudgetReqDto;
import com.example.whatcha.global.entity.BaseEntity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(length = 50)
    private String name;

    private String address;

    @Column(nullable = false, columnDefinition = "varchar(50) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private LocalDate birthDate;

    private String phone;

    @Column(name = "budget_min")
    private Integer budgetMin;

    @Column(name = "budget_max")
    private Integer budgetMax;

    @Column(name = "is_notification_agreed")
    private Boolean isNotificationAgreed;

    @Column(name = "is_location_agreed")
    private Boolean isLocationAgreed;

    @Column(name = "preference_model_id_1")
    private Long preferenceModelId1;

    @Column(name = "preference_model_id_2")
    private Long preferenceModelId2;

    @Column(name = "preference_model_id_3")
    private Long preferenceModelId3;

    private Double latitude;

    private Double longitude;

    @Builder
    public User(String email, String password, String name, String address, UserType userType,
                LocalDate birthDate, String phone, Integer budgetMin, Integer budgetMax,
                Boolean isNotificationAgreed, Boolean isLocationAgreed,
                Long preferenceModelId1, Long preferenceModelId2, Long preferenceModelId3,
                Double latitude, Double longitude) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.userType = (userType != null) ? userType : UserType.ROLE_USER;
        this.birthDate = birthDate;
        this.phone = phone;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.isNotificationAgreed = isNotificationAgreed;
        this.isLocationAgreed = isLocationAgreed;
        this.preferenceModelId1 = preferenceModelId1;
        this.preferenceModelId2 = preferenceModelId2;
        this.preferenceModelId3 = preferenceModelId3;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateBudget(UpdateBudgetReqDto updateBudgetReqDto) {
        this.budgetMin = updateBudgetReqDto.getBudgetMin();
        this.budgetMax = updateBudgetReqDto.getBudgetMax();
    }

    public void updatePreferenceModel(UpdatePreferenceModelReqDto updatePreferenceModelReqDto) {
        this.preferenceModelId1 = updatePreferenceModelReqDto.getPreferenceModelId1();
        this.preferenceModelId2 = updatePreferenceModelReqDto.getPreferenceModelId2();
        this.preferenceModelId3 = updatePreferenceModelReqDto.getPreferenceModelId3();
    }

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
