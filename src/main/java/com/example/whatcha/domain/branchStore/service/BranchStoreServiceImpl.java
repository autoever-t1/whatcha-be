package com.example.whatcha.domain.branchStore.service;

import com.example.whatcha.domain.branchStore.dao.BranchStoreRepository;
import com.example.whatcha.domain.branchStore.domain.BranchStore;
import com.example.whatcha.domain.branchStore.dto.BranchStoreResDto;
import com.example.whatcha.domain.branchStore.exception.BranchStoreNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.whatcha.domain.branchStore.constant.BranchStoreExceptionMessage.BRANCH_STORE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchStoreServiceImpl implements BranchStoreService {
    private final BranchStoreRepository branchStoreRepository;

    @Override
    public BranchStoreResDto findBranchStore(Long branchStoreId) {
        BranchStore branchStore = branchStoreRepository.findById(branchStoreId)
                .orElseThrow(() -> new BranchStoreNotFoundException(BRANCH_STORE_NOT_FOUND.getMessage()));

        return BranchStoreResDto.entityToResDto(branchStore);
    }
}
