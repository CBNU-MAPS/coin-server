package com.maps.coin.controller;

import com.maps.coin.domain.Room;
import com.maps.coin.dto.CreateRoomRequest;
import com.maps.coin.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity<Room> createRoom(@RequestBody CreateRoomRequest request) {
        Room savedRoom = roomService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }
}
