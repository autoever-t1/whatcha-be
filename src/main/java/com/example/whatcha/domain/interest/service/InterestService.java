package com.example.whatcha.domain.interest.service;

import com.example.whatcha.domain.interest.dto.LikedCarResponseDto;
import com.example.whatcha.domain.interest.dto.UserCarAlertResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InterestService {

    Page<LikedCarResponseDto> getLikedCarList(Long userId, Pageable pageable);

    boolean toggleLike(Long userId, Long usedCarId);

    List<UserCarAlertResponseDto> getAlertedModelList(Long userId);

    void deleteAlertByUserAndModel(Long userId, Long modelId);
}
