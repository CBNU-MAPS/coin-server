package com.maps.coin.domain.question;

import com.maps.coin.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "option_question")
public class OptionQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "questionId",nullable = false)
    private Long questionId;

    @Column(name = "question", nullable = false)
    private String question;

    @Builder
    public OptionQuestion(Long questionId, String question) {
        this.questionId = questionId;
        this.question = question;
    }
}
