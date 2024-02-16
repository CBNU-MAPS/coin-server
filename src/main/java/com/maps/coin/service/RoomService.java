package com.maps.coin.service;

import com.maps.coin.domain.question.Question;
import com.maps.coin.domain.room.Problem;
import com.maps.coin.domain.room.Room;
import com.maps.coin.dto.room.CreateRoomRequest;
import com.maps.coin.dto.room.CreateRoomResponse;
import com.maps.coin.dto.room.RoomInfoResponse;
import com.maps.coin.repository.QuestionRepository;
import com.maps.coin.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final QuestionRepository questionRepository;

    public Room save(CreateRoomRequest request) {
        return roomRepository.save(request.toEntity());
    }

    public CreateRoomResponse createRoom(Room room) {
        List<Question> questions = questionRepository.findRandomQuestion(room.getSize());
        questions.stream().forEach(q -> {
            Problem problem = Problem.builder()
                    .room(room)
                    .question(q)
                    .build();

            room.getProblems().add(problem);
            roomRepository.save(room);
        });

        return CreateRoomResponse.builder()
                .roomCode(room.getRoomId())
                .build();
    }

    public RoomInfoResponse readRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);

        List<Problem> problems = room.getProblems();
        List<Question> questions = new ArrayList<>();
        problems.forEach(p -> {
            Question question = p.getQuestion();
            questions.add(Question.builder()
                            .id(question.getId())
                            .type(question.getType())
                            .question(question.getQuestion())
                            .option(question.getOption())
                            .build());
        });

        return RoomInfoResponse.builder()
                .bingoName(room.getName())
                .bingoSize(room.getSize())
                .bingoHeadCount(room.getPersonnel())
                .questions(questions)
                .build();
    }
}
