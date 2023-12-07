package com.example.bequiz.service;

import com.example.bequiz.domain.*;
import com.example.bequiz.dto.CreateQuizDTO;
import com.example.bequiz.dto.QuizDTO;
import com.example.bequiz.dto.RetrieveQuizDTO;
import com.example.bequiz.exception.EntityValidationException;
import com.example.bequiz.exception.ErrorCode;
import com.example.bequiz.repository.*;
import com.example.bequiz.utils.Difficulty;
import com.example.bequiz.utils.EntitiesMapper;
import com.example.bequiz.utils.QuizBooleanBuilder;
import com.example.bequiz.validation.EntitiesValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.bequiz.utils.Constants.QUESTION;
import static com.example.bequiz.utils.Constants.QUIZ;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final EntitiesMapper entitiesMapper;
    private final TagProcessor tagProcessor;
    private final EntitiesValidator entitiesValidator;
    private final QuestionRepository questionRepository;
    private final QuizBooleanBuilder quizBooleanBuilder;

    @Transactional
    public QuizDTO createQuiz(CreateQuizDTO createQuizDTO) {
        entitiesValidator.validateCreateQuizDTO(createQuizDTO);
        List<Tag> tags = tagProcessor.processTags(createQuizDTO.getQuizTags());
        List<Question> questions = getQuestions(createQuizDTO);
        Quiz quiz = Quiz.builder()
                .quizTitle(createQuizDTO.getQuizTitle())
                .difficultyLevel(Difficulty.valueOf(createQuizDTO.getDifficultyLevel().toUpperCase()))
                .isDeleted(false)
                .timeLimitMinutes(createQuizDTO.getTimeLimitMinutes())
                .quizTags(tags)
                .build();
        questions.forEach(question -> {
            if (question.getQuizzes() == null) {
                question.setQuizzes(new ArrayList<>());
            }
            question.getQuizzes().add(quiz);
        });
        quiz.setQuestions(questions);
        return entitiesMapper.quizToQuizDTO(quizRepository.save(quiz));
    }

    private List<Question> getQuestions(CreateQuizDTO createQuizDTO) {
        return createQuizDTO.getQuestions().stream()
                .map(id -> questionRepository.findById(id).orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, QUESTION))).collect(Collectors.toList());
    }

    @Transactional
    public void deleteQuestion(UUID uuid) {
        Quiz quiz = quizRepository.findById(uuid).orElseThrow(() -> new ObjectNotFoundException(uuid, "Quiz Not Found"));
        quiz.setDeleted(true);
        quizRepository.save(quiz);
    }

    @Transactional
    public QuizDTO updateQuiz(UUID id, CreateQuizDTO createQuizDTO) {
        entitiesValidator.validateCreateQuizDTO(createQuizDTO);
        List<Tag> tags = tagProcessor.processTags(createQuizDTO.getQuizTags());
        List<Question> questions = getQuestions(createQuizDTO);
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Quiz not found"));
        quiz.setQuizTitle(createQuizDTO.getQuizTitle());
        quiz.setDifficultyLevel(Difficulty.valueOf(createQuizDTO.getDifficultyLevel().toUpperCase()));
        quiz.setDeleted(false);
        quiz.setTimeLimitMinutes(createQuizDTO.getTimeLimitMinutes());
        quiz.setQuizTags(tags);
        questions.forEach(question -> {
            if (question.getQuizzes() == null) {
                question.setQuizzes(new ArrayList<>());
            }
            if (!question.getQuizzes().contains(quiz)) {
                question.getQuizzes().add(quiz);
            }
        });
        quiz.setQuestions(questions);
        return entitiesMapper.quizToQuizDTO(quizRepository.save(quiz));
    }

    public QuizDTO getQuizById(UUID id) {
        return entitiesMapper.quizToQuizDTO(quizRepository
                .findById(id)
                .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, QUIZ)));
    }

    public Page<RetrieveQuizDTO> findAll(Integer itemsPerPage, Integer pageIndex, String keyword, List<String> difficultyLevels, List<String> tagsAsString) {
        entitiesValidator.validateFindAllFilters(itemsPerPage, pageIndex, tagsAsString, difficultyLevels);
        List<Tag> tags = tagProcessor.getTagsFromTagsAsString(tagsAsString);
        List<Difficulty> enumDifficulties = Optional.ofNullable(difficultyLevels).map(list -> list.stream()
                .map(difficulty -> Difficulty.valueOf(difficulty.toUpperCase())).toList()).orElse(null);

        FindAllFilters quizFilters = FindAllFilters.builder()
                .itemsPerPage(itemsPerPage)
                .pageIndex(pageIndex)
                .keyword(keyword)
                .difficulties(enumDifficulties)
                .tags(tags).build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        if (quizFilters.getItemsPerPage() != null && quizFilters.getPageIndex() != null)
            pageRequest = PageRequest.of(quizFilters.getPageIndex(), quizFilters.getItemsPerPage());

        return quizRepository
                .findAll(quizBooleanBuilder.buildBooleanFromQuizFilters(quizFilters), pageRequest)
                .map(entitiesMapper::quizToRetrieveQuizDTO);
    }

    public Page<RetrieveQuizDTO> searchQuizzesByTags(Integer itemsPerPage, Integer pageIndex, List<String> tagsAsString) {
        entitiesValidator.validatePaginationParams(itemsPerPage, pageIndex);

        PageRequest pageRequest = (itemsPerPage != null && pageIndex != null) ?
                PageRequest.of(pageIndex, itemsPerPage) :
                PageRequest.of(0, 10);

        Sort defaultSort = Sort.by(Sort.Direction.DESC, "dateLastModified", "dateCreated");

        return quizRepository
                .findQuizzesByTagsOrderByTagMatches(tagsAsString, pageRequest.withSort(defaultSort))
                .map(entitiesMapper::quizToRetrieveQuizDTO);
    }
}
