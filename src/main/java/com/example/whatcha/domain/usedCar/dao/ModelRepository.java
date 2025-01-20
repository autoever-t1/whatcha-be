package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findByModelName(String modelName);

    List<Model> findAllByModelName(String modelName);
}
