package com.example.whatcha.domain.interest.service;

import com.example.whatcha.domain.interest.domain.UserCarAlert;
import com.example.whatcha.domain.interest.dto.CarPreviewResponseDto;
import com.example.whatcha.domain.interest.dto.UserCarAlertResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface InterestService {

    Page<CarPreviewResponseDto> getLikedCarList(Long userId, Pageable pageable);

    boolean toggleLike(Long userId, Long usedCarId);

    List<UserCarAlertResponseDto> getAlertedModelList(Long userId);

    void deleteAlertByUserAndModel(Long userId, Long modelId);

    UserCarAlert addUserCarAlert(Long userId, Long modelId, LocalDate alertExpirationDate);

    List<CarPreviewResponseDto> getMostLikedCarList(int limit);
}
