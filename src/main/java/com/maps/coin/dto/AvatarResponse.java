package com.maps.coin.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AvatarResponse {
    private List<Boolean> selectedAvatars;

    @Builder
    public AvatarResponse(List<Boolean> selectedAvatars) { this.selectedAvatars = selectedAvatars; }
}
