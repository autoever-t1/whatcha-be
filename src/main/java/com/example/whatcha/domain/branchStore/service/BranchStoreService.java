package com.example.whatcha.domain.branchStore.service;

import com.example.whatcha.domain.branchStore.dto.BranchStoreResDto;

public interface BranchStoreService {
    BranchStoreResDto findBranchStore(Long branchStoreId);
}
