package com.maps.coin.domain.question;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "question")
public class Question extends BaseEntity {
    private long id;
    private String type;
}
