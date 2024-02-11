package com.maps.coin.dto;

import com.maps.coin.domain.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    private String name;
    private Integer personnel;
    private Integer size;

    public Room toEntity() {
        return Room.builder()
                .name(name)
                .personnel(personnel)
                .size(size)
                .build();
    }
}
