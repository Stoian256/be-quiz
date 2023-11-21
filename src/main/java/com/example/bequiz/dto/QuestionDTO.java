package com.example.bequiz.dto;

import com.example.bequiz.utils.Difficulty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private UUID id;
    private String questionBody;
    private Difficulty difficultly;
    private List<AnswerDTO> answers;
    private List<TagDTO> tags;
    private LocalDateTime dateCreated;
    private String createdBy;
    private LocalDateTime dateLastModified;
    private String lastModifiedBy;
}
