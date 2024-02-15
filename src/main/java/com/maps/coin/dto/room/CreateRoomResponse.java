package com.maps.coin.dto.room;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRoomResponse {
    private UUID roomCode;

    @Builder
    private CreateRoomResponse(UUID roomCode) {
        this.roomCode = roomCode;
    }
}
