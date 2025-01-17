package com.example.whatcha.domain.usedCar.dto.response;

import com.example.whatcha.domain.branchStore.domain.BranchStore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchStoreResDto {

    private final String branchStoreName;
    private final String location;
    private final double latitude;
    private final double longitude;
    private final String phone;

    public static BranchStoreResDto entityToResDto(BranchStore branchStore) {
        return BranchStoreResDto.builder()
                .branchStoreName(branchStore.getBranchStoreName())
                .location(branchStore.getLocation())
                .latitude(branchStore.getLatitude())
                .longitude(branchStore.getLongitude())
                .phone(branchStore.getPhone())
                .build();
    }
}
