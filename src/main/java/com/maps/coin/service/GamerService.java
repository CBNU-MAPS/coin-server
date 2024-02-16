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

    public List<GamerResponse> save(UUID roomId, String sessionId, String name, Integer avatar) {
        if (!roomGamerResponse.containsKey(roomId)) {
            roomGamerResponse.put(roomId, new ArrayList<>());
        }

        Room room = roomRepository.findById(roomId).orElse(null);
        Gamer gamer = Gamer.builder().id(sessionId).room(room).name(name).avatar(avatar).build();
        room.getGamers().add(gamer);
        roomRepository.save(room);

        List<GamerResponse> gamers = roomGamerResponse.get(roomId);
        gamers.add(GamerResponse.builder().name(name).avatar(avatar).ready(false).turn(false).build());
        roomGamerResponse.put(roomId, gamers);
        return gamers;
    }

    public List<GamerResponse> saveReady(UUID roomId, String sessionId, List<AnswerRequest> answers) {
        List<GamerResponse> gamers = roomGamerResponse.get(roomId);
        String name = gamerRepository.findById(sessionId).get().getName();

        gamers.stream().forEach(g -> {
            if (g.getName().equals(name)) g.setReady(true);
        });

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

        return gamers;
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
}

