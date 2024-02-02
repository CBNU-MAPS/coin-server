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
@Table(name = "text_question")
public class TextQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "questionId", nullable = false)
    private Long questionId;

    @Column(name="question", nullable = false)
    private String question;

    @Column(name = "option", nullable = false)
    private String option;

    @Builder
    public TextQuestion(Long questionId, String question, String option) {
        this.questionId = questionId;
        this.question = question;
        this.option = option;
    }
}
