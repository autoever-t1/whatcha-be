package com.example.whatcha.domain.user.dto.response;

import com.example.whatcha.domain.user.constant.UserType;
import com.example.whatcha.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResDto {
    private Long userId;
    private String email;
    private String name;
    private String address;
    private UserType userType;
    private Integer ageGroup;
    private String phone;
    private Integer budgetMin;
    private Integer budgetMax;
    private Boolean isNotificationAgreed;
    private Boolean isLocationAgreed;
    private String preferenceModelName1;
    private String preferenceModelName2;
    private String preferenceModelName3;
    private Double latitude;
    private Double longitude;

    public static UserInfoResDto entityToResDto(User user) {
        return UserInfoResDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .address(user.getAddress())
                .userType(user.getUserType())
                .ageGroup(user.getAgeGroup())
                .phone(user.getPhone())
                .budgetMin(user.getBudgetMin())
                .budgetMax(user.getBudgetMax())
                .isNotificationAgreed(user.getIsNotificationAgreed())
                .isLocationAgreed(user.getIsLocationAgreed())
                .preferenceModelName1(user.getPreferenceModelName1())
                .preferenceModelName2(user.getPreferenceModelName2())
                .preferenceModelName3(user.getPreferenceModelName3())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .build();
    }
}
