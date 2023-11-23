package com.example.bequiz.utils;

import com.example.bequiz.domain.QQuestion;
import com.example.bequiz.domain.QuestionFilters;
import org.springframework.stereotype.Component;
import com.querydsl.core.BooleanBuilder;

@Component
public class QuestionBooleanBuilder {
    public BooleanBuilder buildBooleanFromQuestionFilters(QuestionFilters questionFilters) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (questionFilters.getKeyword() != null) {
            booleanBuilder.and(QQuestion.question.questionTitle.containsIgnoreCase(questionFilters.getKeyword()));
        }

        if (questionFilters.getDifficulty() != null) {
            booleanBuilder.and(QQuestion.question.difficulty.eq(questionFilters.getDifficulty()));
        }

        if (questionFilters.getTags() != null) {
            if (questionFilters.getTags().isEmpty()) {
                booleanBuilder.and(QQuestion.question.tags.isEmpty());
            }
            questionFilters.getTags().forEach(tag -> booleanBuilder.and(QQuestion.question.tags.contains(tag)));
        }

        return booleanBuilder;
    }
}