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
    private String gender;
    private String address;
    private UserType userType;
    private Integer ageGroup;
    private String phone;
    private Integer budgetMin;
    private Integer budgetMax;
    private Boolean isNotificationAgreed;
    private Boolean isLocationAgreed;
    private String preferenceModel1;
    private String preferenceModel2;
    private String preferenceModel3;
    private Double latitude;
    private Double longitude;

    public static UserInfoResDto entityToResDto(User user) {
        return UserInfoResDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .gender(user.getGender())
                .address(user.getAddress())
                .userType(user.getUserType())
                .ageGroup(user.getAgeGroup())
                .phone(user.getPhone())
                .budgetMin(user.getBudgetMin())
                .budgetMax(user.getBudgetMax())
                .isNotificationAgreed(user.getIsNotificationAgreed())
                .isLocationAgreed(user.getIsLocationAgreed())
                .preferenceModel1(user.getPreferenceModel1())
                .preferenceModel2(user.getPreferenceModel2())
                .preferenceModel3(user.getPreferenceModel3())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .build();
    }
}
