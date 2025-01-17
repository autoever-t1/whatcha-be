package com.example.whatcha.domain.usedCar.dao;

import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedCarRepository extends JpaRepository<UsedCar, Long> {
    List<UsedCar> findByBranchStore_BranchStoreId(Long branchStoreId);
}
