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
@Table(name = "question")
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "option")
    private String[] option;

    @Builder
    public Question(String type, String question, String[] option) {
        this.type = type;
        this.question = question;
        this.option = option;
    }
}
