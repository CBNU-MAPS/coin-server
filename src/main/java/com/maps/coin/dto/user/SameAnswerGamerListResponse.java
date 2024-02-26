package com.maps.coin.dto.user;

import com.maps.coin.domain.user.Gamer;
import com.maps.coin.dto.answer.AnswerResponse;
import java.util.List;
import lombok.Builder;

public class SameAnswerGamerListResponse {
    AnswerResponse answer;
    List<Gamer> selectedUsers;

    @Builder
    public SameAnswerGamerListResponse(AnswerResponse answer, List<Gamer> users) {
        this.answer = answer;
        this.selectedUsers = users;
    }
}
