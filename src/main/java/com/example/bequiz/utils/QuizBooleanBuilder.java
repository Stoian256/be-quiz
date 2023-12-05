package com.example.bequiz.utils;

import com.example.bequiz.domain.FindAllFilters;
import com.example.bequiz.domain.QQuiz;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizBooleanBuilder {
    public BooleanBuilder buildBooleanFromQuizFilters(FindAllFilters quizFilters) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (quizFilters.getKeyword() != null) {
            booleanBuilder.and(QQuiz.quiz.quizTitle.containsIgnoreCase(quizFilters.getKeyword()));
        }
        List<Difficulty> difficulties = quizFilters.getDifficulties();
        if (difficulties != null) {
            BooleanBuilder difficultiesOrGroup = new BooleanBuilder();
            difficulties.forEach(difficulty -> difficultiesOrGroup.or(QQuiz.quiz.difficultyLevel.eq(difficulty)));
            booleanBuilder.and(difficultiesOrGroup);
        }
        if (quizFilters.getTags() != null) {
            quizFilters.getTags().forEach(tag -> booleanBuilder.and(QQuiz.quiz.quizTags.contains(tag)));
        }
        booleanBuilder.and(QQuiz.quiz.isDeleted.eq(false));
        return booleanBuilder;
    }
}
