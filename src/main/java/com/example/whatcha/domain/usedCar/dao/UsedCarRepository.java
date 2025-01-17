package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsedCarRepository extends JpaRepository<UsedCar, Long> {
    List<UsedCar> findByPriceBetweenAndModelNameContainingOrModelNameContainingOrModelNameContaining(
            Integer minPrice, Integer maxPrice, String modelName1, String modelName2, String modelName3, Pageable pageable);

    @Query("""
    SELECT uc FROM UsedCar uc
    WHERE uc.usedCarId NOT IN :excludeIds
    ORDER BY uc.createdAt DESC""")
    List<UsedCar> findByIdNotIn(
            @Param("excludeIds") List<Long> excludeIds,
            Pageable pageable);
}
