package com.maps.coin.controller;

import com.maps.coin.dto.board.StatusBoardListResponse;
import com.maps.coin.dto.board.StatusBoardResponse;
import com.maps.coin.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Status Board", description = "Status Board API")
public class StatusBoardController {
    private final GameService gameService;

    @GetMapping("/api/board")
    public ResponseEntity<StatusBoardListResponse> readBingoStatusBoard(@RequestParam String roomCode) {
        List<StatusBoardResponse> board = gameService.findStatusBoardList(UUID.fromString(roomCode));
        Collections.reverse(board);
        return ResponseEntity.status(HttpStatus.OK).body(StatusBoardListResponse.builder().statusBoardList(board)
                .build());
    }
}
