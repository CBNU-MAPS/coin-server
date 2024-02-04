package com.maps.coin.dto;

import com.maps.coin.domain.question.Question;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRoomResponse {
    private UUID roomCode;
    private List<Question> questions;

    @Builder
    private CreateRoomResponse(UUID roomCode, List<Question> questions) {
        this.roomCode = roomCode;
        this.questions = questions;
    }
}
