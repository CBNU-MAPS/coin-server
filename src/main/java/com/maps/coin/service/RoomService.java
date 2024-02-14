package com.maps.coin.service;

import com.maps.coin.domain.question.Question;
import com.maps.coin.domain.room.Problem;
import com.maps.coin.domain.room.Room;
import com.maps.coin.dto.CreateRoomRequest;
import com.maps.coin.dto.CreateRoomResponse;
import com.maps.coin.dto.RoomInfoResponse;
import com.maps.coin.handler.WebSocketHandler;
import com.maps.coin.repository.ProblemRepository;
import com.maps.coin.repository.QuestionRepository;
import com.maps.coin.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final QuestionRepository questionRepository;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);


    public Room save(CreateRoomRequest request) {
        return roomRepository.save(request.toEntity());
    }

    public CreateRoomResponse createRoom(Room room) {
        List<Question> questions = questionRepository.findRandomQuestion(room.getSize());
        questions.stream().forEach(q -> {
            Problem problem = Problem.builder().questionId(q.getId()).room(room).build();
            room.getProblems().add(problem);
            roomRepository.save(room);
        });

        return CreateRoomResponse.builder()
                .roomCode(room.getRoomId())
                .build();
    }

    public RoomInfoResponse readRoom(UUID roomId) {
        LOGGER.info(String.valueOf(roomId));
        Room room = roomRepository.findById(roomId).orElse(null);
        LOGGER.info(room.toString());

        List<Problem> problems = room.getProblems();
        List<Question> questions = new ArrayList<>();
        problems.forEach(p -> {
            Long id = p.getQuestionId();
            Question question = questionRepository.findById(id).orElse(null);
            questions.add(question);
        });
        LOGGER.info(questions.toString());

        return RoomInfoResponse.builder()
                .bingoName(room.getName())
                .bingoSize(room.getSize())
                .bingoHeadCount(room.getPersonnel())
                .questions(questions)
                .build();
    }
}
