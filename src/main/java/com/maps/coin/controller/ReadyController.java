package com.maps.coin.controller;

import com.maps.coin.domain.user.Gamer;
import com.maps.coin.dto.AvatarRequest;
import com.maps.coin.dto.AvatarResponse;
import com.maps.coin.dto.GamerInfoResponse;
import com.maps.coin.dto.GamerRequest;
import com.maps.coin.dto.GamerResponse;
import com.maps.coin.service.AvatarService;
import com.maps.coin.service.SessionService;
import com.maps.coin.service.GamerService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

        List<Boolean> avatars = avatarService.save(roomId, sessionId, message.getAvatar());
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId,
                AvatarResponse.builder().selectedAvatars(avatars).build());
    }

    @MessageMapping("/user")
    public void sendGamersInfo(GamerRequest message, StompHeaderAccessor headerAccessor){
        String sessionId = headerAccessor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        List<GamerResponse> gamers = gamerService.save(roomId, message.getName(), message.getAvatar());
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/user",
            GamerInfoResponse.builder().users(gamers).build());

    }
}
