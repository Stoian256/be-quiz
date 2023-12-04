package com.example.bequiz.dto;

import com.example.bequiz.utils.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {

    private UUID id;
    private LocalDateTime dateCreated;
    private String createdBy;
    private LocalDateTime dateLastModified;
    private String lastModifiedBy;
    private String quizTitle;
    private String difficultyLevel;
    private int timeLimitMinutes;
    private List<TagDTO> quizTags;
    private List<QuestionDTO> questions;
}

