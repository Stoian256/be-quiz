package com.example.bequiz.domain;

import com.example.bequiz.utils.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name="quiz")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
public class Quiz extends BaseEntity{

    private String quizTitle;
    private Difficulty difficultyLevel;
    private int timeLimitMinutes;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name="quiz_tag",
            joinColumns = @JoinColumn(name="quiz_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    private List<Tag> quizTags;

    @ManyToMany
    @JoinTable(
            name="quiz_question",
            joinColumns = @JoinColumn(name="quiz_id"),
            inverseJoinColumns = @JoinColumn(name="question_id")
    )
    private List<Question> questions;

}
