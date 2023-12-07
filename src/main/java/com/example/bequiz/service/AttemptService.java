package com.example.bequiz.service;

import com.example.bequiz.domain.Attempt;
import com.example.bequiz.domain.AttemptQuestion;
import com.example.bequiz.domain.Quiz;
import com.example.bequiz.dto.AttemptDTO;
import com.example.bequiz.exception.EntityValidationException;
import com.example.bequiz.exception.ErrorCode;
import com.example.bequiz.repository.AnswerOptionRepository;
import com.example.bequiz.repository.AttemptQuestionRepository;
import com.example.bequiz.repository.AttemptRepository;
import com.example.bequiz.repository.QuizRepository;
import com.example.bequiz.utils.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.bequiz.utils.Constants.ATTEMPT;
import static com.example.bequiz.utils.Constants.QUIZ;

@Service
@RequiredArgsConstructor
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final AttemptQuestionRepository attemptQuestionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuizRepository quizRepository;
    private final EntitiesMapper entitiesMapper;

    public AttemptDTO startQuiz(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, QUIZ));

        List<AttemptQuestion> questions = quiz.getQuestions().stream().map(entitiesMapper::mapQuestionToAttemptQuestion).collect(Collectors.toList());
        Collections.shuffle(questions);
        questions.forEach(question -> Collections.shuffle(question.getAnswers()));

        Attempt attempt = Attempt.builder()
                .timeLimitMinutes(quiz.getTimeLimitMinutes())
                .quizTitle(quiz.getQuizTitle())
                .difficultyLevel(quiz.getDifficultyLevel())
                .build();

        questions.forEach(question -> {
            attempt.addAttemptQuestion(question);
            attemptQuestionRepository.save(question);

            question.getAnswers().forEach(answerOption -> {
                answerOption.setSelected(false);
                answerOption.setAttemptQuestion(question);
                answerOptionRepository.save(answerOption);
            });

        });

        return entitiesMapper.attemptToAttemptDTO(attemptRepository.save(attempt));
    }

    public Double endQuiz(UUID attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, ATTEMPT));

        long totalCorrectQuestions = attempt.getQuestions().stream()
                .filter(this::areAllCorrectAnswersSelected)
                .count();

        double totalQuestions = attempt.getQuestions().size();
        return totalCorrectQuestions / totalQuestions * 100;
    }

    private boolean areAllCorrectAnswersSelected(AttemptQuestion attemptQuestion) {
        return attemptQuestion.getAnswers().stream()
                .allMatch(answerOption -> answerOption.isCorrectAnswer() == answerOption.isSelected());
    }
}
