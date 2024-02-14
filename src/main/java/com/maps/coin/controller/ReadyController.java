package com.maps.coin.controller;

import com.maps.coin.dto.AvatarRequest;
import com.maps.coin.dto.AvatarResponse;
import com.maps.coin.service.AvatarService;
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
public class ReadyController {
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    private final SessionService sessionService;
    private final AvatarService avatarService;

    @MessageMapping("/avatar")
    public void sendSelectedAvatar(AvatarRequest message, StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        UUID roomId = sessionService.readRoomId(sessionId);

        if (message.getAvatar() > 9) {
            List<Boolean> avatars = avatarService.read(roomId);
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/avatar",
                    AvatarResponse.builder().selectedAvatars(avatars).build());
        }
        else {
            List<Boolean> avatars = avatarService.save(roomId, sessionId, message.getAvatar());
            simpleMessageSendingOperations.convertAndSend("/room/" + roomId + "/avatar",
                    AvatarResponse.builder().selectedAvatars(avatars).build());
        }
    }

}
