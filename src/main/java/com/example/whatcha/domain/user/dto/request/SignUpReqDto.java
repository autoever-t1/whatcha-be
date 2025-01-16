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
    @Length(min = 8, max = 16, message = "비밀번호는 최소 8글자 최대 16글자 입니다.")
    private String password;
    private String name;

    public User dtoToEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .userType(UserType.ROLE_USER)
                .build();
    }
}
