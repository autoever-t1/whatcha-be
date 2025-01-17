package com.example.whatcha.domain.user.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PreferenceModelReqDto {
    private String preferenceModel1;
    private String preferenceModel2;
    private String preferenceModel3;

    public PreferenceModelReqDto(String preferenceModel1, String preferenceModel2, String preferenceModel3) {
        this.preferenceModel1 = preferenceModel1;
        this.preferenceModel2 = preferenceModel2;
        this.preferenceModel3 = preferenceModel3;
    }
}
