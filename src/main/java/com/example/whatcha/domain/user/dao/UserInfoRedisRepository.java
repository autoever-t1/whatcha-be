package com.example.whatcha.domain.user.dao;

import com.example.whatcha.domain.user.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserInfoRedisRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findByEmail(String email);
}
