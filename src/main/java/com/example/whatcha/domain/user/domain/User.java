package com.example.whatcha.domain.user.domain;

import com.example.whatcha.domain.user.constant.UserType;
import com.example.whatcha.domain.user.dto.request.UpdateUserReqDto;
import com.example.whatcha.global.entity.BaseEntity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(length = 16)
    private String nickname;

    @Column(nullable = false, columnDefinition = "varchar(50) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Builder
    public User(String email, String password, String nickname, UserType userType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userType = (userType != null) ? userType : UserType.ROLE_USER;
    }

    public void updateUser(UpdateUserReqDto updateUserReqDto) {
        this.nickname = updateUserReqDto.getNickname();
    }

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

}
