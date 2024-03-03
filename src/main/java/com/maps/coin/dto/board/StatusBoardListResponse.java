package com.maps.coin.dto.board;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusBoardListResponse {
    private List<StatusBoardResponse> statusBoardList;

    @Builder
    public StatusBoardListResponse(List<StatusBoardResponse> statusBoardList) {
        this.statusBoardList = statusBoardList;
    }
}
