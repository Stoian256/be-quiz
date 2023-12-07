package com.example.bequiz.dto;

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
public class AttemptDTO {
    private UUID attemptId;
    private LocalDateTime startedAt;
    private int timeLimit;
    private List<AttemptQuestionDTO> questions;
}
