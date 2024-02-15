package com.maps.coin.repository;

import com.maps.coin.domain.user.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerRepository extends JpaRepository<Gamer, String> {
}
