package com.maps.coin.controller;

import com.maps.coin.domain.user.Gamer;
import com.maps.coin.dto.answer.AnswerRequest;
import com.maps.coin.dto.answer.AnswerResponse;
import com.maps.coin.dto.user.GamerInfoResponse;
import com.maps.coin.dto.user.GamerResponse;
import com.maps.coin.dto.user.SameAnswerGamerListResponse;
import com.maps.coin.service.GameService;
import com.maps.coin.service.GamerService;
import com.maps.coin.service.SessionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    private final SessionService sessionService;
    private final GamerService gamerService;
    private final GameService gameService;

    @MessageMapping("/select")
    public void selectAnswer(AnswerRequest message, StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        List<GamerResponse> gamers = gamerService.readNextTurnGamer(roomId);
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/users",
                GamerInfoResponse.builder().users(gamers).build());

        AnswerResponse answer = AnswerResponse.builder().answerRequest(message).build();
        List<Gamer> selectedGamers = gameService
                .findSameAnswerGamer(roomId, message.getQuestionId(), message.getAnswer());
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/select",
                SameAnswerGamerListResponse.builder().answer(answer).users(selectedGamers).build());
    }
}
