package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
