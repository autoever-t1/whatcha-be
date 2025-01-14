package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
