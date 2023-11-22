package com.example.bequiz.utils;

import com.example.bequiz.domain.Answer;
import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.AnswerDTO;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.dto.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class EntitiesMapper {
    public QuestionDTO questionToQuestionDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .questionTitle(question.getQuestionTitle())
                .questionBody(question.getQuestionBody())
                .difficultly(question.getDifficultly())
                .answers(question.getAnswers().stream().map(this::answerToAnswerDTO).toList())
                .tags(question.getTags().stream().map(this::tagToTagDTO).toList())
                .dateCreated(question.getDateCreated())
                .createdBy(question.getCreatedBy())
                .dateLastModified(question.getDateLastModified())
                .lastModifiedBy(question.getLastModifiedBy())
                .build();
    }

    public AnswerDTO answerToAnswerDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .answerContent(answer.getAnswerContent())
                .isCorrectAnswer(answer.isCorrectAnswer())
                .build();
    }

    public TagDTO tagToTagDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .tagTitle(tag.getTagTitle())
                .build();
    }
}
