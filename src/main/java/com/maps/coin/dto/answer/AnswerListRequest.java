package com.maps.coin.dto.answer;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerListRequest {
    private List<AnswerRequest> answers;
}
