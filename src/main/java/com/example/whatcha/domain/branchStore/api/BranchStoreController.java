package com.example.whatcha.domain.branchStore.api;

import com.example.whatcha.domain.branchStore.dto.BranchStoreResDto;
import com.example.whatcha.domain.branchStore.service.BranchStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branch-store")
public class BranchStoreController {

    private final BranchStoreService branchStoreService;

    @GetMapping("/{branchStoreId}")
    public ResponseEntity<BranchStoreResDto> findBranchStore(@PathVariable Long branchStoreId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(branchStoreService.findBranchStore(branchStoreId));
    }
}
