package com.maps.coin.controller;

import com.maps.coin.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@RequiredArgsConstructor
public class StompController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        LOGGER.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();

        LOGGER.info("sessionId Disconnected : " + sessionId);
    }

    @MessageMapping("/roomCode")
    @SendTo("/room")
    public String sendMessage(String params) {

        LOGGER.info("Send Message");
        return params + ": Here";
    }
}