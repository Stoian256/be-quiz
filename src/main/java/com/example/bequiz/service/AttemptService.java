package com.example.bequiz.service;

import com.example.bequiz.domain.AnswerOption;
import com.example.bequiz.domain.Attempt;
import com.example.bequiz.domain.AttemptQuestion;
import com.example.bequiz.domain.Quiz;
import com.example.bequiz.dto.AddAnswerRequestDTO;
import com.example.bequiz.dto.AttemptDTO;
import com.example.bequiz.exception.EntityValidationException;
import com.example.bequiz.exception.ErrorCode;
import com.example.bequiz.repository.AnswerOptionRepository;
import com.example.bequiz.repository.AttemptQuestionRepository;
import com.example.bequiz.repository.AttemptRepository;
import com.example.bequiz.repository.QuizRepository;
import com.example.bequiz.utils.EntitiesMapper;
import com.example.bequiz.validation.EntitiesValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.bequiz.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final AttemptQuestionRepository attemptQuestionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuizRepository quizRepository;
    private final EntitiesMapper entitiesMapper;
    private final EntitiesValidator entitiesValidator;

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

    public void addAnswer(AddAnswerRequestDTO addAnswerRequestDTO) {
        Attempt attempt = attemptRepository.findById(addAnswerRequestDTO.getAttemptId())
                .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, ATTEMPT));

        AttemptQuestion attemptQuestion = attempt.getQuestions().stream()
                .filter(question -> question.getId().equals(addAnswerRequestDTO.getQuestionId()))
                .findFirst()
                .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, QUESTION));

        List<UUID> selectedAnswers = addAnswerRequestDTO.getSelectedAnswers();
        List<AnswerOption> possibleAnswers = attemptQuestion.getAnswers();
        entitiesValidator.validateSelectedAnswersIds(selectedAnswers,possibleAnswers);

        attemptQuestion.getAnswers().forEach(answerOption -> {
            answerOption.setSelected(addAnswerRequestDTO.getSelectedAnswers().contains(answerOption.getAnswerOptionId()));
            answerOptionRepository.save(answerOption);
        });
    }
}
