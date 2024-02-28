package com.maps.coin.domain.user;

import com.maps.coin.domain.BaseEntity;
import com.maps.coin.domain.question.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answer")
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "selected", nullable = true)
    private Boolean selected;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "gamer_id", referencedColumnName = "id")
    private Gamer gamer;

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    @Builder
    public Answer(String answer, Question question, Gamer gamer) {
        this.answer = answer;
        this.question = question;
        this.gamer = gamer;
    }
}
