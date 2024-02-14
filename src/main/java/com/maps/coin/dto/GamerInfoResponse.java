package com.maps.coin.dto;

import com.maps.coin.domain.user.Gamer;
import java.util.List;
import lombok.Builder;

public class GamerInfoResponse {
    private List<GamerResponse> users;

    @Builder
    public GamerInfoResponse(List<GamerResponse> users){this.users = users;}
}
