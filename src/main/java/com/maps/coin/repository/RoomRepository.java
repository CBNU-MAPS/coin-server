package com.maps.coin.repository;

import com.maps.coin.domain.room.Room;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
