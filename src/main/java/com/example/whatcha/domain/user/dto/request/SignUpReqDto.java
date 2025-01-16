package com.example.whatcha.domain.user.dto.request;

import com.example.whatcha.domain.user.constant.UserType;
import com.example.whatcha.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@Builder
public class SignUpReqDto {
    @Email
    private String email;
    private String name;
    private Integer ageGroup;
    private String phone;
    private String address;
    private Double latitude;
    private Double longitude;
    private String appToken;

    public User dtoToEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .ageGroup(ageGroup)
                .phone(phone)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .userType(UserType.ROLE_USER)
                .appToken(appToken)
                .build();
    }
}
