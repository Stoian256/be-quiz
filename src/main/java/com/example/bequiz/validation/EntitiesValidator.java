package com.example.bequiz.validation;

import com.example.bequiz.dto.CreateAnswerDTO;
import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.dto.CreateQuizDTO;
import com.example.bequiz.exception.EntityValidationException;
import com.example.bequiz.exception.ErrorCode;
import com.example.bequiz.utils.Difficulty;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.bequiz.utils.Constants.*;

@Component
public class EntitiesValidator {

    public void validateFindAllFilters(Integer itemsPerPage, Integer pageIndex, List<String> tagsAsString, List<String> difficulties) {
        if (itemsPerPage != null && itemsPerPage < 1)
            throw new EntityValidationException(ErrorCode.INVALID_ITEMS_PER_PAGE);
        if (pageIndex != null && pageIndex < 0)
            throw new EntityValidationException(ErrorCode.INVALID_PAGE_INDEX);
        if (tagsAsString != null && tagsAsString.size() > 7)
            throw new EntityValidationException(ErrorCode.INVALID_NUMBER_OF_TAGS);
        if (difficulties != null) try {
            difficulties.forEach(difficulty -> Difficulty.valueOf(difficulty.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new EntityValidationException(ErrorCode.INVALID_DIFFICULTY);
        }
    }

    public void validateCreateQuestionDTO(CreateQuestionDTO createQuestionDTO) {
        if (createQuestionDTO.getQuestionTitle() == null || createQuestionDTO.getQuestionTitle().isEmpty()) {
            throw new EntityValidationException(ErrorCode.INVALID_FIELD, QUESTION_TITLE);
        }
        if (createQuestionDTO.getDifficulty() == null) {
            throw new EntityValidationException(ErrorCode.INVALID_FIELD, DIFFICULTY);
        }
        if (createQuestionDTO.getTags() == null) {
            throw new EntityValidationException(ErrorCode.INVALID_FIELD, TAG_LIST);
        }
        if (createQuestionDTO.getAnswers() == null) {
            throw new EntityValidationException(ErrorCode.INVALID_FIELD, ANSWERS);
        }
        if (createQuestionDTO.getTags().stream().distinct().count() != createQuestionDTO.getTags().size()) {
            throw new EntityValidationException(ErrorCode.DUPLICATE_TAGS);
        }
        try {
            Difficulty.valueOf(createQuestionDTO.getDifficulty().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new EntityValidationException(ErrorCode.INVALID_DIFFICULTY);
        }
        if (createQuestionDTO.getAnswers().size() < 2)
            throw new EntityValidationException(ErrorCode.INVALID_NUMBER_OF_ANSWERS);
        if (createQuestionDTO.getTags().size() > 7)
            throw new EntityValidationException(ErrorCode.INVALID_NUMBER_OF_TAGS);
        if (createQuestionDTO.getAnswers().stream().noneMatch(CreateAnswerDTO::isCorrectAnswer))
            throw new EntityValidationException(ErrorCode.INVALID_NUMBER_OF_CORRECT_ANSWERS);
    }

    public void validateCreateQuizDTO(CreateQuizDTO createQuizDTO) {
        if (createQuizDTO.getQuizTitle() == null || createQuizDTO.getQuizTitle().isBlank()) {
            throw new EntityValidationException(ErrorCode.INVALID_FIELD, QUIZ_TITLE);
        }
        if (createQuizDTO.getDifficultyLevel() == null) {
            throw new EntityValidationException(ErrorCode.INVALID_FIELD, DIFFICULTY);
        }
        if (createQuizDTO.getQuestions() == null || createQuizDTO.getQuestions().isEmpty()) {
            throw new EntityValidationException(ErrorCode.INVALID_NUMBER_OF_QUESTIONS);
        }
        if (createQuizDTO.getQuizTags() == null) {
            throw new EntityValidationException(ErrorCode.INVALID_FIELD, TAG_LIST);
        }
        if (createQuizDTO.getQuizTags().size() > 7) {
            throw new EntityValidationException(ErrorCode.INVALID_NUMBER_OF_TAGS);
        }
        try {
            Difficulty.valueOf(createQuizDTO.getDifficultyLevel().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new EntityValidationException(ErrorCode.INVALID_DIFFICULTY);
        }
        if (createQuizDTO.getTimeLimitMinutes() < 1) {
            throw new EntityValidationException(ErrorCode.INVALID_TIME_LIMIT, QUIZ_TIME_LIMIT);
        }
    }
}