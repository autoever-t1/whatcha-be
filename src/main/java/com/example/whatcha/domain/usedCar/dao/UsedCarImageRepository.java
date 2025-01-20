package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedCarImageRepository extends JpaRepository<UsedCarImage, Long> {

    List<UsedCarImage> findByUsedCar_UsedCarId(Long usedCarId);
}
