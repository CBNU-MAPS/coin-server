package com.maps.coin.domain.question;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "text_question")
public class TextQuestion extends BaseEntity {
    private Long id;
    private Long questionId;
    private String question;
    private String option;
}
