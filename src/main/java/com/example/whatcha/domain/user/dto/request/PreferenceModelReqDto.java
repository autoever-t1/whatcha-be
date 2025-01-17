package com.example.whatcha.domain.user.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PreferenceModelReqDto {
    private String preferenceModelName1;
    private String preferenceModelName2;
    private String preferenceModelName3;

    public PreferenceModelReqDto(String preferenceModelName1, String preferenceModelName2, String preferenceModelName3) {
        this.preferenceModelName1 = preferenceModelName1;
        this.preferenceModelName2 = preferenceModelName2;
        this.preferenceModelName3 = preferenceModelName3;
    }
}
