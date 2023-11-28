package com.example.bequiz.dto;

import com.example.bequiz.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuizDTO {

    private String quizTitle;
    private String difficultyLevel;
    private int timeLimitMinutes;
    private List<String> quizTags;
    private List<Question> questions;
}
