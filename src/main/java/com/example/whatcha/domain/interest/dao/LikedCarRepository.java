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
    Page<LikedCar> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT lc.usedCar.usedCarId, COUNT(lc) " +
            "FROM LikedCar lc " +
            "WHERE lc.usedCar.usedCarId IN (" +
            "  SELECT distinct subLc.usedCar.usedCarId " +
            "  FROM LikedCar subLc " +
            "  WHERE subLc.userId = :userId" +
            ") AND lc.isLiked = true " +
            "GROUP BY lc.usedCar.usedCarId")
    List<Object[]> findLikeCountsForUserLikedCars(@Param("userId") Long userId);

    Optional<LikedCar> findByUserIdAndUsedCar_UsedCarId(Long userId, Long usedCarId);
}
