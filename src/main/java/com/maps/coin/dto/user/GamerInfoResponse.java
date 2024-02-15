package com.maps.coin.dto.user;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GamerInfoResponse {
    private List<GamerResponse> users;

    @Builder
    public GamerInfoResponse(List<GamerResponse> users){this.users = users;}
}
