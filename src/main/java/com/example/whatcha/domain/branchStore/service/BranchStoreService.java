package com.example.whatcha.domain.branchStore.service;

import com.example.whatcha.domain.usedCar.dto.response.BranchStoreResDto;

public interface BranchStoreService {
    BranchStoreResDto findBranchStore(Long branchStoreId);
}
