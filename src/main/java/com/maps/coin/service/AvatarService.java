package com.maps.coin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {
    private Map<UUID, List<Boolean>> roomAvatar = new HashMap<>();
    private Map<String, Integer> userAvatar = new HashMap<>();

    public List<Boolean> save(UUID roomId, String sessionId, Integer avatar) {
        if (roomAvatar.get(roomId) == null) {
            roomAvatar.put(roomId, new ArrayList<Boolean>(Collections.nCopies(10, false)));
        }

        List<Boolean> avatars = roomAvatar.get(roomId);
        if (userAvatar.get(sessionId) == null) {
            userAvatar.put(sessionId, avatar);
            avatars.set(avatar, true);
        }
        else {
            Integer beforeAvatar = userAvatar.get(sessionId);
            avatars.set(beforeAvatar, false);

            userAvatar.put(sessionId, avatar);
            avatars.set(avatar, true);
        }
        return roomAvatar.put(roomId, avatars);
    }

    public List<Boolean> read(UUID roomId) {
        if (roomAvatar.get(roomId) == null) {
            roomAvatar.put(roomId, new ArrayList<Boolean>(Collections.nCopies(10, false)));
        }

        List<Boolean> avatars = roomAvatar.get(roomId);
        return avatars;
    }


    public List<Boolean> remove(UUID roomId, String sessionId){
        if(userAvatar.containsKey(sessionId)){
            Integer avatar = userAvatar.get(sessionId);

            List<Boolean> avatars = roomAvatar.get(roomId);
            avatars.set(avatar, false);
            roomAvatar.put(roomId, avatars);

            userAvatar.remove(sessionId);

            return avatars;
        }
        return null;
    }
}
