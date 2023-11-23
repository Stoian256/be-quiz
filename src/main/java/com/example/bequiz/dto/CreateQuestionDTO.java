package com.example.bequiz.dto;

import com.example.bequiz.domain.Answer;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionDTO {

<<<<<<< HEAD
    private String questionTitle;
    private String questionBody;
    private String difficulty;
=======
    private String questionBody;
    private String difficultly;
>>>>>>> 50caf894050c1e9e077cdabdc083650aed7aad54
    private List<Answer> answers;
    private List<String> tags;
}
