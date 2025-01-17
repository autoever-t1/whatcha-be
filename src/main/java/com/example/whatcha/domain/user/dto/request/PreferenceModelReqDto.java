package com.example.whatcha.domain.user.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PreferenceModelReqDto {
    private Long preferenceModelId1;
    private Long preferenceModelId2;
    private Long preferenceModelId3;

    public PreferenceModelReqDto(Long preferenceModelId1, Long preferenceModelId2, Long preferenceModelId3) {
        this.preferenceModelId1 = preferenceModelId1;
        this.preferenceModelId2 = preferenceModelId2;
        this.preferenceModelId3 = preferenceModelId3;
    }
}
