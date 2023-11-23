package com.example.bequiz.utils;

import com.example.bequiz.domain.Answer;
import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.AnswerDTO;
import com.example.bequiz.dto.CreateAnswerDTO;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.dto.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntitiesMapper {
    QuestionDTO questionToQuestionDTO(Question question);

    TagDTO tagToTagDTO(Tag tag);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    AnswerDTO answerToAnswerDTO(Answer answer);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    Answer createAnswerDTOToAnswer(CreateAnswerDTO createAnswerDTO);

}