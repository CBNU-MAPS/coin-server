package com.maps.coin.repository;

import com.maps.coin.domain.room.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

}
