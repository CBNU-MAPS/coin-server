package com.maps.coin.dto;

import com.maps.coin.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    private String title;

    public Room toEntity() {
        return Room.builder()
                .title(title)
                .build();
    }
}
