package com.maps.coin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GamerResponse {
    private String name;
    private Boolean ready;
    private Boolean turn;
    private Integer avatar;

    @Builder
    public GamerResponse(Integer avatar, String name, Boolean ready, Boolean turn){
        this.avatar = avatar;
        this.name = name;
        this.ready = ready;
        this.turn = turn;
    }
}
