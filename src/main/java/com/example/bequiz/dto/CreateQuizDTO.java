package com.example.bequiz.dto;

import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.utils.Difficulty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuizDTO {


    private String quizTitle;
    private String difficultyLevel;
    private int timeLimit;
    private List<String> tags;
    private List<Question> questions;
}
