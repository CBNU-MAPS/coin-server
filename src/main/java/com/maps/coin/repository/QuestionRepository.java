package com.maps.coin.repository;

import com.maps.coin.domain.question.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM question order by RANDOM() limit :size * :size", nativeQuery = true)
    List<Question> findRandomQuestion(@Param(value="size") Integer size);
}
