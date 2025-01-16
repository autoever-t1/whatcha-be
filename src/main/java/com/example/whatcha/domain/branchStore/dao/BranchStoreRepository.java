package com.example.whatcha.domain.branchStore.dao;

import com.example.whatcha.domain.branchStore.domain.BranchStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchStoreRepository extends JpaRepository<BranchStore, Long> {

    Optional<BranchStore> findByBranchStoreNameContaining(String branchStoreName);
}
