package com.maps.coin.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {
    private Long questionId;
    private String answer;

    @Builder
    public AnswerResponse(AnswerRequest answerRequest) {
        this.questionId = answerRequest.getQuestionId();
        this.answer = answerRequest.getAnswer();
    }
}
