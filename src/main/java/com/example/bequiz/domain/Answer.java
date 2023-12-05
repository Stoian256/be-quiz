package com.example.bequiz.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Where;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true,exclude = {"question"})
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer")
@Where(clause = "is_deleted = false")
public class Answer extends BaseEntity {

    private String answerContent;

    private boolean isCorrectAnswer;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
