package com.example.bequiz.dto;

import com.example.bequiz.domain.Answer;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionDTO {

    private String questionTitle;
    private String questionBody;
    private String difficulty;
    private List<Answer> answers;
    private List<String> tags;
}
