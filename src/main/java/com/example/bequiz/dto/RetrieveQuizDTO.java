package com.example.bequiz.dto;

import com.example.bequiz.utils.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveQuizDTO {
    private UUID id;
    private String quizTitle;
    private Difficulty difficultyLevel;
    private List<TagDTO> quizTags;
    private Integer numberOfQuestions;
    private int timeLimitMinutes;
}
