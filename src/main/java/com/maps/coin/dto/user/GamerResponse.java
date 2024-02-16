package com.maps.coin.dto.user;

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

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    @Builder
    public GamerResponse(Integer avatar, String name, Boolean ready, Boolean turn){
        this.avatar = avatar;
        this.name = name;
        this.ready = ready;
        this.turn = turn;
    }
}
