package com.maps.coin.service;

import com.maps.coin.domain.room.Room;
import com.maps.coin.domain.user.Gamer;
import com.maps.coin.dto.CreateRoomRequest;
import com.maps.coin.dto.GamerInfoResponse;
import com.maps.coin.dto.GamerResponse;
import com.maps.coin.repository.GamerRepository;
import com.maps.coin.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GamerService {
    private final RoomRepository roomRepository;
    private Map<UUID, List<GamerResponse>> roomGamerResponse = new HashMap<>();

    public List<GamerResponse> save(UUID roomId, String name, Integer avatar) {
        if (!roomGamerResponse.containsKey(roomId)) {
            roomGamerResponse.put(roomId, new ArrayList<>());
        }

        Room room = roomRepository.findById(roomId).orElse(null);
        Gamer gamer = Gamer.builder().name(name).avatar(avatar).build();
        room.getGamers().add(gamer);

        List<GamerResponse> gamers = roomGamerResponse.get(roomId);
        gamers.add(GamerResponse.builder().name(name).avatar(avatar).ready(false).turn(false).build());
        roomGamerResponse.put(roomId, gamers);
        return gamers;
    }
}

