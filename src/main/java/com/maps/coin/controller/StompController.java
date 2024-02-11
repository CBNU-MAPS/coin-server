package com.maps.coin.controller;

import com.maps.coin.handler.WebSocketHandler;
import com.maps.coin.service.RoomService;
import com.maps.coin.service.SessionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    private final RoomService roomService;
    private final SessionService sessionService;

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
        simpleMessageSendingOperations.convertAndSend("/room/" + roomCode,
                roomService.readRoom(roomCode));
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();

        LOGGER.info("sessionId Disconnected : " + sessionId);
    }
}