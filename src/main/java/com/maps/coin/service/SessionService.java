package com.maps.coin.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private Map<String, UUID> sessions = new HashMap<>();

    public void save(String sessionId, UUID roomId) { sessions.put(sessionId, roomId); }

    public UUID readRoomId(String sessionId) { return sessions.get(sessionId); }
}
