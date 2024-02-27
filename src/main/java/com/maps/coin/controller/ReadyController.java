package com.maps.coin.controller;

import com.maps.coin.dto.answer.AnswerListRequest;
import com.maps.coin.dto.avatar.AvatarRequest;
import com.maps.coin.dto.avatar.AvatarResponse;
import com.maps.coin.dto.user.GamerInfoResponse;
import com.maps.coin.dto.user.GamerRequest;
import com.maps.coin.dto.user.GamerResponse;
import com.maps.coin.handler.WebSocketHandler;
import com.maps.coin.service.AvatarService;
import com.maps.coin.service.SessionService;
import com.maps.coin.service.GamerService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReadyController {
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    private final SessionService sessionService;
    private final AvatarService avatarService;
    private final GamerService gamerService;

    @MessageMapping("/avatar")
    public void sendSelectedAvatar(AvatarRequest message, StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        List<Boolean> avatars;
        if (message.getAvatar() > 9) avatars = avatarService.read(roomId);
        else avatars = avatarService.save(roomId, sessionId, message.getAvatar());

        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/avatar",
                AvatarResponse.builder().selectedAvatars(avatars).build());
    }

    @MessageMapping("/user")
    public void sendGamersInfo(GamerRequest message, StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        GamerResponse gamer = gamerService.save(roomId, sessionId, message.getUserName(), message.getAvatar());
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/user",
            gamer);
    }

    @MessageMapping("/ready")
    public void sendGamersReadyInfo(AnswerListRequest message, StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        GamerResponse gamer = gamerService.saveReady(roomId, sessionId, message.getAnswers());
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/ready",
                gamer);

        if (gamerService.readStartStatus(roomId)) {
            List<GamerResponse> gamers = gamerService.readNextTurnGamer(roomId);
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/start",
                    GamerInfoResponse.builder().users(gamers).build());
        }
    }
}