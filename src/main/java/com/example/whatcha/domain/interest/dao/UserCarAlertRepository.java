package com.example.whatcha.domain.interest.dao;

import com.example.whatcha.domain.interest.domain.UserCarAlert;
import com.example.whatcha.domain.usedCar.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserCarAlertRepository extends JpaRepository<UserCarAlert, Long> {

    List<UserCarAlert> findAllByUserId(Long userId);
    void deleteByUserIdAndModel_ModelId(Long userId, Long modelId);
    Optional<UserCarAlert> findByUserIdAndModel(Long userId, Model model);

    @Modifying
    @Query("DELETE FROM UserCarAlert u WHERE u.alertExpirationDate < :date")
    int deleteExpiredAlerts(LocalDate date);

}
