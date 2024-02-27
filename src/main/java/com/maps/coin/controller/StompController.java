package com.maps.coin.controller;

import com.maps.coin.dto.avatar.AvatarResponse;
import com.maps.coin.dto.user.GamerInfoResponse;
import com.maps.coin.dto.user.GamerResponse;
import com.maps.coin.handler.WebSocketHandler;
import com.maps.coin.service.AvatarService;
import com.maps.coin.service.GamerService;
import com.maps.coin.service.RoomService;
import com.maps.coin.service.SessionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Controller
@RequiredArgsConstructor
public class StompController {

    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    private final RoomService roomService;
    private final SessionService sessionService;
    private final AvatarService avatarService;
    private final GamerService gamerService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();
        String roomCode = headerAccesor.getFirstNativeHeader("code");

        sessionService.save(sessionId, UUID.fromString(roomCode));
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();
        UUID roomCode = sessionService.readRoomId(sessionId);

        if (headerAccesor.getDestination().matches(".*/room")) {
            simpleMessageSendingOperations.convertAndSend("/room/" + roomCode + "/room",
                    roomService.readRoom(roomCode));
        }

        if (headerAccesor.getDestination().matches(".*/users")) {
            List<GamerResponse> gamers = gamerService.read(roomCode);
            simpleMessageSendingOperations.convertAndSend("/room/" + roomCode + "/users",
                    GamerInfoResponse.builder().users(gamers).build());
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        sessionService.remove(sessionId, roomId);

        List<Boolean> avatars = avatarService.remove(roomId, sessionId);
        if (avatars != null) {
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/avatar",
                AvatarResponse.builder().selectedAvatars(avatars).build());
        }

        GamerResponse gamer = gamerService.remove(roomId, sessionId);
        simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/delete",
            gamer);
    }
}