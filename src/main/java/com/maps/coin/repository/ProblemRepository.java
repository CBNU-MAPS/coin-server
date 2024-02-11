package com.maps.coin.repository;

import com.maps.coin.domain.room.Problem;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByRoom_RoomId(UUID id);
}
