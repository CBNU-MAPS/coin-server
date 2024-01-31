package com.maps.coin.dto;

import com.maps.coin.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    private String name;
    private Long personnel;
    private Long size;

    public Room toEntity() {
        return Room.builder()
                .name(name)
                .personnel(personnel)
                .size(size)
                .build();
    }
}
