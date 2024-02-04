package com.maps.coin.domain.room;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "problem")
public class Problem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roomId", nullable = false)
    private UUID roomId;

    @Column(name = "questionId", nullable = false)
    private Long questionId;

    @Builder
    public Problem(UUID roomId, Long questionId) {
        this.roomId = roomId;
        this.questionId = questionId;
    }
}
