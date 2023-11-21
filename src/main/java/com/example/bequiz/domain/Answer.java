package com.example.bequiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@QueryEntity
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
