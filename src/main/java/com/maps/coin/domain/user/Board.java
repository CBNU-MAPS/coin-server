package com.maps.coin.domain.user;

import com.maps.coin.dto.answer.AnswerBoardIndex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

@Getter
@Setter
public class Board {
    Map<Long, Pair<Integer, Integer>> position;
    List<List<AnswerBoardIndex>> board;

    public Board(List<AnswerBoardIndex> answers, Integer size) {
        this.position = new HashMap<>();
        this.board = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            List<AnswerBoardIndex> row = answers.subList(i * size, (i + 1) * size);

            for (int j = 0; j < size; j++) {
                AnswerBoardIndex answer = row.get(j);
                this.position.put(answer.getQuestionId(), Pair.of(i, j));
            }
            this.board.add(new ArrayList<>(row));
        }
    }
}
