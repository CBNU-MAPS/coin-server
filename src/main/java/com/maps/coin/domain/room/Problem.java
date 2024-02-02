package com.maps.coin.domain.room;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "problem")
public class Problem extends BaseEntity {
    private Long id;
    private Long roomId;
    private Long questionId;
}
