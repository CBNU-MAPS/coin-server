package com.maps.coin.repository;

import com.maps.coin.domain.user.Gamer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerRepository extends JpaRepository<Gamer, UUID> {
    List<Gamer> findByRoom_RoomId(UUID id);
}
