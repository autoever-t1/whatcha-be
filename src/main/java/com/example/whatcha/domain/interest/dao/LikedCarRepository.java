package com.example.whatcha.domain.interest.dao;

import com.example.whatcha.domain.interest.domain.LikedCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedCarRepository extends JpaRepository<LikedCar, Long> {
//    Page<LikedCar> findByUserId(Long userId, Pageable pageable);
}
