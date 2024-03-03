package com.maps.coin.controller;

import com.maps.coin.dto.avatar.AvatarResponse;
import com.maps.coin.dto.room.RoomInfoResponse;
import com.maps.coin.dto.user.GamerInfoResponse;
import com.maps.coin.dto.user.GamerResponse;
import com.maps.coin.service.AvatarService;
import com.maps.coin.service.GameService;
import com.maps.coin.service.GamerService;
import com.maps.coin.service.RoomService;
import com.maps.coin.service.SessionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
    private final GameService gameService;

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
            if (!roomService.readRoomAccessPermission(roomCode)) {
                simpleMessageSendingOperations.convertAndSend("/room/" + roomCode + "/room", "");
            } else {
                RoomInfoResponse roomInfo = roomService.readRoom(roomCode);
                simpleMessageSendingOperations.convertAndSend("/room/" + roomCode + "/room",
                        roomInfo);
                gameService.saveProblemOrder(sessionId, roomInfo.getQuestions());
            }
        }

        if (headerAccesor.getDestination().matches(".*/users")) {
            List<GamerResponse> gamers = gamerService.read(roomCode);
            simpleMessageSendingOperations.convertAndSend("/room/" + roomCode + "/users",
                    GamerInfoResponse.builder().users(gamers).build());
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event)
            throws InterruptedException {
        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccesor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);
        Boolean isTurn = gamerService.readTurnStatus(roomId, sessionId);


        sessionService.remove(sessionId, roomId);

        List<Boolean> avatars = avatarService.remove(roomId, sessionId);
        if (avatars != null) {
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/avatar",
                AvatarResponse.builder().selectedAvatars(avatars).build());
        }

        GamerResponse gamer = gamerService.remove(roomId, sessionId);
        if (gamer != null) {
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/delete",
                    gamer);
        }

        if (isTurn) {
            List<GamerResponse> gamers = gamerService.readNextTurnGamer(roomId);
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/users",
                    GamerInfoResponse.builder().users(gamers).build());
        }

        if (gamerService.readStartStatus(roomId)) {
            List<GamerResponse> gamers = gamerService.readNextTurnGamer(roomId);
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/start",
                    GamerInfoResponse.builder().users(gamers).build());
        }

        roomService.deleteRoomIfEmpty(roomId);
    }
}