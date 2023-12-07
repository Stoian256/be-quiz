package com.example.bequiz.domain;

import com.example.bequiz.utils.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attempt_question")
public class AttemptQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String questionTitle;

    private String questionBody;

    private Difficulty difficulty;

    @OneToMany(mappedBy = "attemptQuestion", cascade = CascadeType.ALL)
    private List<AnswerOption> answers;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "questions")
    private List<Attempt> attempts;

    public void addAttempt(Attempt attempt) {
        attempts = Optional.ofNullable(attempts).orElse(new ArrayList<>());

        if (!attempts.contains(attempt)) {
            attempts.add(attempt);
            attempt.addAttemptQuestion(this);
        }
    }
}
