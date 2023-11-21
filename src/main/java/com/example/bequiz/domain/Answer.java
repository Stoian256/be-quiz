package com.example.bequiz.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer")
public class Answer extends BaseEntity {

    private String answerContent;

    private boolean isCorrectAnswer;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
