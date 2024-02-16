package com.maps.coin.domain.question;

import com.maps.coin.domain.BaseEntity;
import com.maps.coin.domain.room.Problem;
import com.maps.coin.domain.user.Answer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "option")
    private String[] option;

    @OneToMany(mappedBy="question", cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();

    @OneToMany(mappedBy="question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Question(Long id, String type, String question, String[] option) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.option = option;
    }
}
