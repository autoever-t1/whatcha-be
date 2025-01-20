package com.example.whatcha.domain.interest.dao;

import com.example.whatcha.domain.interest.domain.LikedCar;
import com.example.whatcha.domain.usedCar.domain.UsedCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikedCarRepository extends JpaRepository<LikedCar, Long> {

    Page<LikedCar> findByUserIdAndIsLikedTrue(Long userId, Pageable pageable);

    Optional<LikedCar> findByUserIdAndUsedCar_UsedCarId(Long userId, Long usedCarId);

    Optional<LikedCar> findIsLikedByUserIdAndUsedCar_UsedCarId(Long userId, Long usedCarId);

    @Query("SELECT lc.usedCar " +
            "FROM LikedCar lc " +
            "WHERE lc.isLiked = true " +
            "GROUP BY lc.usedCar " +
            "ORDER BY COUNT(lc) DESC")
    List<UsedCar> findTopLikedCars(Pageable pageable);

}
