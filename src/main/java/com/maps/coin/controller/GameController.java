package com.maps.coin.controller;

import com.maps.coin.dto.answer.AnswerRequest;
import com.maps.coin.dto.user.GamerInfoResponse;
import com.maps.coin.dto.user.GamerResponse;
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

    @MessageMapping("/select")
    public void selectAnswer(AnswerRequest message, StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        List<GamerResponse> gamers = gamerService.readNextTurnGamer(roomId);
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/user",
                GamerInfoResponse.builder().users(gamers).build());
    }
}
