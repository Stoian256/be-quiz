package com.example.bequiz.utils;

import com.example.bequiz.domain.QQuestion;
import com.example.bequiz.domain.QuestionFilters;
import org.springframework.stereotype.Component;
import com.querydsl.core.BooleanBuilder;

import java.util.List;

@Component
public class QuestionBooleanBuilder {
    public BooleanBuilder buildBooleanFromQuestionFilters(QuestionFilters questionFilters) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (questionFilters.getKeyword() != null) {
            booleanBuilder.and(QQuestion.question.questionTitle.containsIgnoreCase(questionFilters.getKeyword()));
        }

        List<Difficulty> difficulties = questionFilters.getDifficulties();
        BooleanBuilder difficultiesOrGroup = new BooleanBuilder();
        if (difficulties != null && !difficulties.isEmpty()) {
            difficulties.forEach(difficulty -> difficultiesOrGroup.or(QQuestion.question.difficulty.eq(difficulty)));
        }
        booleanBuilder.and(difficultiesOrGroup);

        if (questionFilters.getTags() != null) {
            questionFilters.getTags().forEach(tag -> booleanBuilder.and(QQuestion.question.tags.contains(tag)));
        }
        booleanBuilder.and(QQuestion.question.isDeleted.eq(false));
        return booleanBuilder;
    }
}