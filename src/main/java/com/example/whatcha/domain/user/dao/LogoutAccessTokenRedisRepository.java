package com.example.whatcha.domain.user.dao;

import com.example.whatcha.domain.user.domain.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
