package com.maps.coin.service;

import com.maps.coin.domain.question.Question;
import com.maps.coin.domain.room.Room;
import com.maps.coin.domain.user.Gamer;
import com.maps.coin.domain.user.Answer;
import com.maps.coin.dto.answer.AnswerRequest;
import com.maps.coin.dto.user.GamerResponse;
import com.maps.coin.repository.AnswerRepository;
import com.maps.coin.repository.GamerRepository;
import com.maps.coin.repository.QuestionRepository;
import com.maps.coin.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GamerService {
    private final RoomRepository roomRepository;
    private final GamerRepository gamerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private Map<UUID, List<GamerResponse>> roomGamerResponse = new HashMap<>();

    public GamerResponse save(UUID roomId, String sessionId, String name, Integer avatar) {
        if (!roomGamerResponse.containsKey(roomId)) {
            roomGamerResponse.put(roomId, new ArrayList<>());
        }

        Room room = roomRepository.findById(roomId).orElse(null);
        Gamer gamer = Gamer.builder().id(sessionId).room(room).name(name).avatar(avatar).build();
        room.getGamers().add(gamer);
        roomRepository.save(room);

        List<GamerResponse> gamers = roomGamerResponse.get(roomId);
        GamerResponse gamerResponse = GamerResponse.builder().name(name).avatar(avatar).ready(false).turn(false).build();
        gamers.add(gamerResponse);
        roomGamerResponse.put(roomId, gamers);

        return gamerResponse;
    }

    public GamerResponse saveReady(UUID roomId, String sessionId, List<AnswerRequest> answers) {
        List<GamerResponse> gamers = roomGamerResponse.get(roomId);
        String name = gamerRepository.findById(sessionId).get().getName();
        Integer avatar = gamerRepository.findById(sessionId).get().getAvatar();

        answers.stream().forEach(a -> {
            Question question = questionRepository.findById(a.getQuestionId()).orElse(null);
            Gamer gamer = gamerRepository.findById(sessionId).orElse(null);

            Answer answer = Answer.builder()
                    .answer(a.getAnswer())
                    .question(question)
                    .gamer(gamer)
                    .build();
            answerRepository.save(answer);
        });

        GamerResponse gamer = GamerResponse.builder().name(name).avatar(avatar).ready(true).turn(false).build();
        return gamer;
    }

    public List<GamerResponse> read(UUID roomId) {
        return roomGamerResponse.get(roomId);
    }

    public Boolean readStartStatus(UUID roomId) {
        List<GamerResponse> gamers = roomGamerResponse.get(roomId);

        Boolean start = true;
        for (GamerResponse g : gamers) {
            if (!g.getReady()) {
                start = false;
                break;
            }
        }
        return start;
    }

    public List<GamerResponse> readNextTurnGamer(UUID roomId) {
        List<GamerResponse> gamers = roomGamerResponse.get(roomId);

        int isTurn = -1;
        for (int i = 0; i < gamers.size(); i++) {
            if (gamers.get(i).getTurn()) {
                isTurn = i;
                break;
            }
        }

        if (isTurn != -1) {
            gamers.get(isTurn).setTurn(false);
            if (isTurn == gamers.size() - 1) isTurn = -1;
            gamers.get(++isTurn).setTurn(true);
        }
        else {
            gamers.get(0).setTurn(true);
        }
        roomGamerResponse.put(roomId, gamers);
        return gamers;
    }

    public GamerResponse remove(UUID roomId, String sessionId) {
        if (roomGamerResponse.containsKey(roomId)) {
            List<GamerResponse> gamers = roomGamerResponse.get(roomId);
            Integer avatar = gamerRepository.findById(sessionId).get().getAvatar();

            gamers.removeIf(g -> gamerRepository.findById(sessionId)
                .map(gamer->gamer.getName().equals(g.getName()))
                .orElse(false));

            roomGamerResponse.put(roomId, gamers);

            gamerRepository.deleteById(sessionId);

            GamerResponse gamer = GamerResponse.builder().avatar(avatar).build();
            return gamer;
        } return null;
    }
}

