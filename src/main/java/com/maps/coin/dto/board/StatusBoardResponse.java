package com.maps.coin.dto.board;

import com.maps.coin.dto.user.GamerResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusBoardResponse {
    private String question;
    private String answer;
    private List<GamerResponse> selectedUsers;

    @Builder
    public StatusBoardResponse(String question, String answer, List<GamerResponse> selectedUsers) {
        this.question = question;
        this.answer = answer;
        this.selectedUsers = selectedUsers;
    }
}
