package com.example.whatcha.domain.user.domain;

import com.example.whatcha.domain.user.constant.UserType;
import com.example.whatcha.domain.user.dto.request.BudgetReqDto;
import com.example.whatcha.domain.user.dto.request.ConsentReqDto;
import com.example.whatcha.domain.user.dto.request.PreferenceModelReqDto;
import com.example.whatcha.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 50)
    private String name;

    private String address;

    @Column(nullable = false, columnDefinition = "varchar(50) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private Integer ageGroup;

    private String phone;

    @Column(name = "budget_min")
    private Integer budgetMin;

    @Column(name = "budget_max")
    private Integer budgetMax;

    @Column(name = "is_notification_agreed")
    private Boolean isNotificationAgreed;

    @Column(name = "is_location_agreed")
    private Boolean isLocationAgreed;

    @Column(name = "preference_model_1")
    private String preferenceModel1;

    @Column(name = "preference_model_2")
    private String preferenceModel2;

    @Column(name = "preference_model_3")
    private String preferenceModel3;

    private Double latitude;

    private Double longitude;

    private String appToken;

    private String gender;


    @Builder
    public User(String email, String name, String address, UserType userType,
                Integer ageGroup, String phone, Integer budgetMin, Integer budgetMax,
                Boolean isNotificationAgreed, Boolean isLocationAgreed,
                String preferenceModel1, String preferenceModel2, String preferenceModel3,
                Double latitude, Double longitude, String appToken, String gender) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.userType = (userType != null) ? userType : UserType.ROLE_USER;
        this.ageGroup = ageGroup;
        this.phone = phone;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.isNotificationAgreed = isNotificationAgreed;
        this.isLocationAgreed = isLocationAgreed;
        this.preferenceModel1 = preferenceModel1;
        this.preferenceModel2 = preferenceModel2;
        this.preferenceModel3 = preferenceModel3;
        this.latitude = latitude;
        this.longitude = longitude;
        this.appToken = appToken;
        this.gender = gender;
    }

    public void updateBudget(BudgetReqDto budgetReqDto) {
        this.budgetMin = budgetReqDto.getBudgetMin();
        this.budgetMax = budgetReqDto.getBudgetMax();
    }

    public void updateConsent(ConsentReqDto consentReqDto) {
        this.isNotificationAgreed = consentReqDto.getIsNottificationAgreed();
        this.isLocationAgreed = consentReqDto.getIsLocationAgreed();
    }

    public void updatePreferenceModel(PreferenceModelReqDto preferenceModelReqDto) {
        this.preferenceModel1 = preferenceModelReqDto.getPreferenceModel1();
        this.preferenceModel2 = preferenceModelReqDto.getPreferenceModel2();
        this.preferenceModel3 = preferenceModelReqDto.getPreferenceModel3();
    }
}
