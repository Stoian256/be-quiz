package com.example.bequiz.utils;

import com.example.bequiz.domain.*;
import com.example.bequiz.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntitiesMapper {

    @Mapping(target = "difficulty" ,source = "question.difficulty")
    QuestionDTO questionToQuestionDTO(Question question);

    TagDTO tagToTagDTO(Tag tag);
    List<TagDTO> tagListToTagDTOList(List<Tag> tags);

    @Mapping(target = "difficultyLevel" ,source = "quiz.difficultyLevel")
    QuizDTO quizToQuizDTO(Quiz quiz);

    List<Question> questionDTOToQuestion(List<QuestionDTO> questionDTOList);

    @Mapping(target = "tagTitle", source = "tag")
    Tag mapStringToTag(String tag);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    AnswerDTO answerToAnswerDTO(Answer answer);

    @Mapping(target = "isCorrectAnswer", source = "correctAnswer")
    Answer createAnswerDTOToAnswer(CreateAnswerDTO createAnswerDTO);


    default String mapDifficulty(Difficulty difficulty){
        if (difficulty !=null){
            StringBuilder difficultyToString=new StringBuilder(difficulty.toString());
            return difficultyToString.substring(0,1).toUpperCase() +difficultyToString.substring(1).toLowerCase();
        }
        return null;
    }

    @Mapping(target = "numberOfQuestions", source = "questions")
    RetrieveQuizDTO quizToRetrieveQuizDTO(Quiz quiz);

    default Integer mapQuestionsToNumberOfQuestions(List<Question> questions) {
        return questions != null ? questions.size() : 0;
    }
}