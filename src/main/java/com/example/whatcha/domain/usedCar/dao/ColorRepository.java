package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.Color;
import com.example.whatcha.domain.usedCar.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Long> {
    Optional<Color> findByColorName(String colorName);
}
