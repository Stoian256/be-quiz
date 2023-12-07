package com.example.bequiz.domain;

import com.example.bequiz.utils.Difficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID attemptId;

    @CreatedBy
    private String userId;

    @CreatedDate
    private LocalDateTime startedAt;

    private String quizTitle;

    private Difficulty difficultyLevel;

    private int timeLimitMinutes;

    @ManyToMany
    @JoinTable(
            name = "attempt_question_association",
            joinColumns = @JoinColumn(name = "attempt_id"),
            inverseJoinColumns = @JoinColumn(name = "attempt_question_id")
    )
    private List<AttemptQuestion> questions;
}
