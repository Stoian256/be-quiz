package com.example.bequiz.utils;

import com.example.bequiz.domain.Answer;
import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Quiz;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntitiesMapper {
    QuestionDTO questionToQuestionDTO(Question question);

    TagDTO tagToTagDTO(Tag tag);
    QuizDTO quizToQuizDTO(Quiz quiz);

    List<Question> questionDTOToQuestion(List<QuestionDTO> questionDTOList);

    @Mapping(target = "tagTitle",source = "tag")
    Tag mapStringToTag(String tag);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    AnswerDTO answerToAnswerDTO(Answer answer);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    Answer createAnswerDTOToAnswer(CreateAnswerDTO createAnswerDTO);

}