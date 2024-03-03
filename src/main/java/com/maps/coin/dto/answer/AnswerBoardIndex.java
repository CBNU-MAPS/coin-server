package com.maps.coin.dto.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerBoardIndex {
    private String answer;
    private Boolean selected;
    private Long questionId;

    @Builder
    public AnswerBoardIndex(String answer, Long questionId) {
        this.answer = answer;
        this.selected = false;
        this.questionId = questionId;
    }
}
