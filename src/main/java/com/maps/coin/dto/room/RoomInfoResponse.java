package com.maps.coin.dto.room;

import com.maps.coin.domain.question.Question;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomInfoResponse {
    private String bingoName;
    private Integer bingoSize;
    private Integer bingoHeadCount;
    private List<Question> questions;

    @Builder
    public RoomInfoResponse(
            String bingoName, Integer bingoSize,
            Integer bingoHeadCount, List<Question> questions
    ) {
        this.bingoName = bingoName;
        this.bingoSize = bingoSize;
        this.bingoHeadCount = bingoHeadCount;
        this.questions = questions;
    }
}
