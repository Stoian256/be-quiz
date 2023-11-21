package com.example.bequiz.domain;

import com.example.bequiz.utils.Difficulty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFilters {
    Integer itemsPerPage;
    Integer pageIndex;
    String keyword;
    Difficulty difficulty;
    List<Tag> tags;
}
