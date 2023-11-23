package com.example.bequiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnswerDTO {
    private String answerContent;
    private boolean isCorrectAnswer;
}
