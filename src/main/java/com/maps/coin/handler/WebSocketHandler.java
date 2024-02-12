package com.maps.coin.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketHandler implements ChannelInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        LOGGER.info(String.valueOf(accessor.getCommand()));
    }


}
