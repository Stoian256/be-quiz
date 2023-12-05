package com.example.bequiz.utils;

import com.example.bequiz.domain.QQuestion;
import com.example.bequiz.domain.FindAllFilters;
import org.springframework.stereotype.Component;
import com.querydsl.core.BooleanBuilder;

import java.util.List;

@Component
public class QuestionBooleanBuilder {
    public BooleanBuilder buildBooleanFromQuestionFilters(FindAllFilters questionFilters) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (questionFilters.getKeyword() != null) {
            booleanBuilder.and(QQuestion.question.questionTitle.containsIgnoreCase(questionFilters.getKeyword()));
        }

        List<Difficulty> difficulties = questionFilters.getDifficulties();
        if (difficulties != null) {
            BooleanBuilder difficultiesOrGroup = new BooleanBuilder();
            difficulties.forEach(difficulty -> difficultiesOrGroup.or(QQuestion.question.difficulty.eq(difficulty)));
            booleanBuilder.and(difficultiesOrGroup);
        }

        if (questionFilters.getTags() != null) {
            questionFilters.getTags().forEach(tag -> booleanBuilder.and(QQuestion.question.tags.contains(tag)));
        }
        booleanBuilder.and(QQuestion.question.isDeleted.eq(false));
        return booleanBuilder;
    }
}