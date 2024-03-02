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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GamerService {
    private final RoomRepository roomRepository;
    private final GamerRepository gamerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SessionService sessionService;

    private Map<UUID, List<GamerResponse>> roomGamerResponse = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(GamerService.class);

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
            List<Answer> answerList = gamer.getAnswers();

            Answer answer = answerList.stream()
                    .filter(q -> a.getQuestionId().equals(q.getQuestion().getId()))
                    .findFirst()
                    .orElse(null);

            if (answer == null) {
                answer = Answer.builder()
                        .answer(a.getAnswer())
                        .question(question)
                        .gamer(gamer)
                        .build();
            } else {
                answer.setAnswer(a.getAnswer());
            }

            answerRepository.save(answer);
        });

        Boolean ready = false;
        for (GamerResponse g: gamers) {
            if (g.getAvatar().equals(avatar)) {
                Boolean gamerReady = g.getReady();
                ready = gamerReady ^ true;
                g.setReady(ready);
            }
        }

        roomGamerResponse.put(roomId, gamers);
        GamerResponse gamer = GamerResponse.builder().name(name).avatar(avatar).ready(ready).turn(false).build();
        return gamer;
    }

    public List<GamerResponse> read(UUID roomId) {
        return roomGamerResponse.get(roomId);
    }

    public Boolean readStartStatus(UUID roomId) {
        List<GamerResponse> gamers = roomGamerResponse.get(roomId);
        Integer count = sessionService.readSessionCount(roomId);
        logger.info(String.valueOf(count));
        logger.info(String.valueOf(gamers.size()));
        if (!count.equals(gamers.size())) return false;

        Boolean start = true;
        for (GamerResponse g : gamers) {
            if (!g.getReady()) {
                start = false;
                break;
            }
        }

        Room room = roomRepository.findById(roomId).orElse(null);
        room.setStart(true);
        return start;
    }

    public Boolean readTurnStatus(UUID roomId, String sessionId) {
        Boolean turn = false;
        List<GamerResponse> gamers = roomGamerResponse.get(roomId);

        Gamer gamer = gamerRepository.findById(sessionId).orElse(null);
        if (gamer == null) return false;

        Integer avatar = gamer.getAvatar();
        for (int i = 0; i < gamers.size(); i++) {
            if (avatar.equals(gamers.get(i).getAvatar())) {
                if (gamers.get(i).getTurn() == true) turn = true;
            }
        }
        return turn;
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
            Gamer gamer = gamerRepository.findById(sessionId).orElse(null);
            if (gamer == null) return null;

            Integer avatar = gamer.getAvatar();
            gamers.removeIf(g -> gamerRepository.findById(sessionId)
                .map(g2->g2.getName().equals(g.getName()))
                .orElse(false));

            roomGamerResponse.put(roomId, gamers);
            gamerRepository.deleteById(sessionId);
          
            GamerResponse gamerResponse = GamerResponse.builder().avatar(avatar).build();
            return gamerResponse;
        }
        return null;
    }
}

