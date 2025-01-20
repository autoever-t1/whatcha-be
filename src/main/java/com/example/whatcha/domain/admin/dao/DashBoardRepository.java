package com.example.whatcha.domain.admin.dao;

import com.example.whatcha.domain.admin.domain.DashBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DashBoardRepository extends JpaRepository<DashBoard, Integer> {
    Optional<DashBoard> findByDate(LocalDate today);
}
