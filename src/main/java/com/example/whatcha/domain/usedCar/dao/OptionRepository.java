package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
