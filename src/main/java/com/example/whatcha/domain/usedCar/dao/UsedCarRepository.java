package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsedCarRepository extends JpaRepository<UsedCar, Long>, JpaSpecificationExecutor<UsedCar> {

    @Query("SELECT u FROM UsedCar u WHERE u.modelName LIKE %:keyword% OR u.vhclRegNo LIKE %:keyword%")
    Page<UsedCar> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
