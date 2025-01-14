package com.example.whatcha.domain.interest.service;

import com.example.whatcha.domain.interest.dto.LikedCarResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterestService {

//    Page<LikedCarResponseDto> getLikedCarList(Pageable pageable);

    boolean toggleLike();
}
