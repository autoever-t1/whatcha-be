package com.example.whatcha.domain.branchStore.dto;

import com.example.whatcha.domain.branchStore.domain.BranchStore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchStoreResDto {

    private Long branchStoreId;
    private String branchStoreName;
    private String location;
    private double latitude;
    private double longitude;
    private Integer ownedCarCount;
    private String phone;

    public static BranchStoreResDto entityToResDto(BranchStore branchStore) {
        return BranchStoreResDto.builder()
                .branchStoreId(branchStore.getBranchStoreId())
                .branchStoreName(branchStore.getBranchStoreName())
                .location(branchStore.getLocation())
                .latitude(branchStore.getLatitude())
                .longitude(branchStore.getLongitude())
                .ownedCarCount(branchStore.getOwnedCarCount())
                .phone(branchStore.getPhone())
                .build();
    }
}
