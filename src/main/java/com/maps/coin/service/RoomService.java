package com.maps.coin.service;

import com.maps.coin.domain.Room;
import com.maps.coin.dto.CreateRoomRequest;
import com.maps.coin.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room save(CreateRoomRequest request) {
        return roomRepository.save(request.toEntity());
    }
}
