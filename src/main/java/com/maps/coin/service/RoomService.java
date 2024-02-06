package com.maps.coin.service;

import com.maps.coin.domain.question.Question;
import com.maps.coin.domain.room.Problem;
import com.maps.coin.domain.room.Room;
import com.maps.coin.dto.CreateRoomRequest;
import com.maps.coin.dto.CreateRoomResponse;
import com.maps.coin.repository.ProblemRepository;
import com.maps.coin.repository.QuestionRepository;
import com.maps.coin.repository.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final QuestionRepository questionRepository;
    private final ProblemRepository problemRepository;

    public Room save(CreateRoomRequest request) {
        return roomRepository.save(request.toEntity());
    }

    public CreateRoomResponse createRoom(Room room) {
        List<Question> quesrions = questionRepository.findRandomQuestion(room.getSize());
        quesrions.stream().forEach(q -> problemRepository.save(
                Problem.builder().roomId(room.getRoomId()).questionId(q.getId()).build()));

        return CreateRoomResponse.builder()
                .roomCode(room.getRoomId())
                .questions(quesrions)
                .build();
    }
}
