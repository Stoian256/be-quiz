package com.example.bequiz.dto;

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
public class AttemptQuestionDTO {
    private UUID questionId;
    private String questionTitle;
    private String questionBody;
    private List<AnswerOptionDTO> answersOptions;
}
