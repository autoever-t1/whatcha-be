package com.example.whatcha.domain.user.dao;

import com.example.whatcha.domain.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByEmail(String email);
}
