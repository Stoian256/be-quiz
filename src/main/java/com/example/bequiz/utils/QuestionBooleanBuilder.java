package com.example.bequiz.utils;

import com.example.bequiz.domain.QQuestion;
import com.example.bequiz.domain.QuestionFilters;
import com.example.bequiz.domain.Tag;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
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

        if (questionFilters.getDifficulty() != null) {
            booleanBuilder.and(QQuestion.question.difficultly.eq(questionFilters.getDifficulty()));
        }

        if (questionFilters.getTags() != null) {
            questionFilters.getTags().forEach(tag -> booleanBuilder.and(QQuestion.question.tags.contains(tag)));
        }

        return booleanBuilder;
    }
}