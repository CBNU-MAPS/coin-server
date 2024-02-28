package com.maps.coin.service;

import com.maps.coin.domain.question.Question;
import com.maps.coin.domain.room.Room;
import com.maps.coin.domain.user.Answer;
import com.maps.coin.domain.user.Board;
import com.maps.coin.domain.user.Gamer;
import com.maps.coin.dto.board.StatusBoardListResponse;
import com.maps.coin.dto.board.StatusBoardResponse;
import com.maps.coin.dto.user.GamerResponse;
import com.maps.coin.repository.QuestionRepository;
import com.maps.coin.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
    private final RoomRepository roomRepository;
    private final QuestionRepository questionRepository;

    private Map<String, Board> boards = new HashMap<>();
    private Map<UUID, List<StatusBoardResponse>> statusBoards = new HashMap<>();

    private void createGamerBoard(Gamer gamer, Integer size) {
        List<Answer> answers = gamer.getAnswers();
        boards.put(gamer.getId(), new Board(answers, size));
    }

    public void createRoomGamerBoard(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        List<Gamer> gamers = room.getGamers();
        gamers.stream().forEach(g -> createGamerBoard(g, room.getSize()));
    }

    public List<GamerResponse> findSameAnswerGamer(UUID roomId, Long questionId, String answer) {
        Room room = roomRepository.findById(roomId).orElse(null);
        List<Gamer> gamers = room.getGamers();

        List<GamerResponse> gamerResponseList = new ArrayList<>();
        gamers.stream().forEach(g -> {
            String sessionId = g.getId();
            if (!boards.containsKey(sessionId)) createGamerBoard(g, room.getSize());

            Board board = boards.get(sessionId);
            Pair<Integer, Integer> pos = board.getPosition().get(questionId);

            List<List<Answer>> gamerAnswerBoard = board.getBoard();
            Answer gamerAnswer = gamerAnswerBoard.get(pos.getFirst()).get(pos.getSecond());

            if (gamerAnswer.getAnswer().equals(answer)) {
                gamerAnswer.setSelected(true);
                gamerAnswerBoard.get(pos.getFirst()).set(pos.getSecond(), gamerAnswer);
                board.setBoard(gamerAnswerBoard);
                boards.put(sessionId, board);
                gamerResponseList.add(GamerResponse.builder().name(g.getName()).avatar(g.getAvatar()).build());
            }
        });

        List<StatusBoardResponse> statusBoard = statusBoards.get(roomId);
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            statusBoard.add(StatusBoardResponse.builder().answer(answer).question(question.getQuestion())
                    .selectedUsers(gamerResponseList).build());
            statusBoards.put(roomId, statusBoard);
        }
        return gamerResponseList;
    }

    public List<StatusBoardResponse> findStatusBoardList(UUID roomId) {
        if (!statusBoards.containsKey(roomId)) statusBoards.put(roomId, new ArrayList<>());

        List<StatusBoardResponse> statusBoard = statusBoards.get(roomId);
        return statusBoard;
    }
}
