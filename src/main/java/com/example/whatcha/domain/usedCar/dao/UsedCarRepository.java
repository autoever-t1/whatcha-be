package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedCarRepository extends JpaRepository<UsedCar, Long> {
}
