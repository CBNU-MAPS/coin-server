package com.maps.coin.domain.question;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "option_question")
public class OptionQuestion extends BaseEntity {
    private Long id;
    private Long questionId;
    private String question;
}
