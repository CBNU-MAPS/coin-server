package com.maps.coin.controller;

import com.maps.coin.domain.room.Room;
import com.maps.coin.dto.room.CreateRoomRequest;
import com.maps.coin.dto.room.CreateRoomResponse;
import com.maps.coin.dto.room.RoomInfoResponse;
import com.maps.coin.dto.user.GamerInfoResponse;
import com.maps.coin.dto.user.GamerResponse;
import com.maps.coin.service.GamerService;
import com.maps.coin.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Room", description = "Room API")
public class RoomController {
    private final RoomService roomService;
    private final GamerService gamerService;

    @PostMapping("/api/room")
    @Operation(summary = "Post Room Information", description = "방을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "방 생성에 성공했습니다.",
                    content = {@Content(schema = @Schema(implementation = Room.class))}),
            @ApiResponse(responseCode = "404", description = "방 생성에 실패했습니다."),
    })
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest request) {
        Room savedRoom = roomService.save(request);
        CreateRoomResponse createdRoom = roomService.createRoom(savedRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping("/api/room")
    @Operation(summary = "Get Room Information", description = "방 정보를 보내준다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "방 정보 검색에 성공했습니다.",
                    content = {@Content(schema = @Schema(implementation = Room.class))}),
    })
    public ResponseEntity<RoomInfoResponse> findRoom(@RequestParam String roomCode) {
        if (!roomService.readRoomAccessPermission(UUID.fromString(roomCode))) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            RoomInfoResponse roomInfo = roomService.readRoom(UUID.fromString(roomCode));
            return ResponseEntity.status(HttpStatus.OK).body(roomInfo);
        }
    }

    @GetMapping("/api/users")
    @Operation(summary = "Get Users Information", description = "방에 존재하는 유저 정보를 전달한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 검색에 성공했습니다.",
                    content = {@Content(schema = @Schema(implementation = Room.class))}),
    })
    public ResponseEntity<GamerInfoResponse> findUsers(@RequestParam String roomCode) {
        List<GamerResponse> gamers = gamerService.read(UUID.fromString(roomCode));
        return ResponseEntity.status(HttpStatus.OK).body(GamerInfoResponse.builder().users(gamers).build());
    }
}
