package com.example.bequiz.utils;

import com.example.bequiz.domain.*;
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

    @Mapping(target = "tagTitle", source = "tag")
    Tag mapStringToTag(String tag);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    AnswerDTO answerToAnswerDTO(Answer answer);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    Answer createAnswerDTOToAnswer(CreateAnswerDTO createAnswerDTO);

    @Mapping(target = "numberOfQuestions", source = "questions")
    RetrieveQuizDTO quizToRetrieveQuizDTO(Quiz quiz);

    default Integer mapQuestionsToNumberOfQuestions(List<Question> questions) {
        return questions != null ? questions.size() : 0;
    }
}