package com.maps.coin.service;

import com.maps.coin.domain.room.Room;
import com.maps.coin.domain.user.Gamer;
import com.maps.coin.dto.CreateRoomRequest;
import com.maps.coin.dto.GamerInfoResponse;
import com.maps.coin.dto.GamerResponse;
import com.maps.coin.repository.GamerRepository;
import com.maps.coin.repository.RoomRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamerService {
    private final RoomRepository roomRepository;
    private final GamerRepository gamerRepository;
    private final GamerResponse gamerResponse;
    private Map<UUID, List<GamerResponse>> roomGamerResponse = new HashMap<>();

    public List<GamerResponse> save(UUID roomId, String name, Integer avatar) {
        Room room = roomRepository.findById(roomId).orElse(null);
        room.getGamers().add(Gamer.builder().name(name).avatar(avatar).build());


        List<GamerResponse> gamers = roomGamerResponse.get(roomId);
        gamers.add(GamerResponse.builder().name(name).avatar(avatar).ready(false).turn(false).build());
        roomGamerResponse.put(roomId,gamers);
        return gamers;
    }
}

