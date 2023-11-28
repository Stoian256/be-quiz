package com.example.bequiz.service;

import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Quiz;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.CreateQuizDTO;
import com.example.bequiz.dto.QuizDTO;
import com.example.bequiz.repository.QuizRepository;
import com.example.bequiz.utils.Difficulty;
import com.example.bequiz.utils.EntitiesMapper;
import com.example.bequiz.validation.EntitiesValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizService{

    private final QuizRepository quizRepository;
    private final EntitiesMapper entitiesMapper;
    private final TagProcessor tagProcessor;
    private final EntitiesValidator entitiesValidator;

    @Transactional
    public QuizDTO createQuiz(CreateQuizDTO createQuizDTO) {
        entitiesValidator.validateCreateQuizDTO(createQuizDTO);
        List<Tag> tags = tagProcessor.processTags(createQuizDTO.getQuizTags());
        List<Question> questions =entitiesMapper.questionDTOToQuestion(createQuizDTO.getQuestions());
        Quiz quiz = Quiz.builder()
                .quizTitle(createQuizDTO.getQuizTitle())
                .difficultyLevel(Difficulty.valueOf(createQuizDTO.getDifficultyLevel().toUpperCase()))
                .isDeleted(false)
                .timeLimitMinutes(createQuizDTO.getTimeLimitMinutes())
                .quizTags(tags)
                .build();
        questions.forEach(question -> {
            if (question.getQuizzes()==null){
                question.setQuizzes(new ArrayList<>());
            }
            question.getQuizzes().add(quiz);
        });
        quiz.setQuestions(questions);
       return entitiesMapper.quizToQuizDTO(quizRepository.save(quiz));
    }

    @Transactional
    public QuizDTO updateQuiz(UUID id, CreateQuizDTO createQuizDTO){
        entitiesValidator.validateCreateQuizDTO(createQuizDTO);
        List<Tag> tags = tagProcessor.processTags(createQuizDTO.getQuizTags());
        List<Question> questions = entitiesMapper.questionDTOToQuestion(createQuizDTO.getQuestions());
        Quiz quiz=quizRepository.findById(id).orElseThrow(()->new ObjectNotFoundException(id,"Quiz not found"));
               quiz.setQuizTitle(createQuizDTO.getQuizTitle());
                quiz.setDifficultyLevel(Difficulty.valueOf(createQuizDTO.getDifficultyLevel().toUpperCase()));
                quiz.setDeleted(false);
                quiz.setTimeLimitMinutes(createQuizDTO.getTimeLimitMinutes());
                quiz.setQuizTags(tags);
        questions.forEach(question -> {
            if (question.getQuizzes()==null){
                question.setQuizzes(new ArrayList<>());
            }
            if (!question.getQuizzes().contains(quiz)){
                question.getQuizzes().add(quiz);
            }
        });
        quiz.setQuestions(questions);
        return entitiesMapper.quizToQuizDTO(quizRepository.save(quiz));
    }
}
