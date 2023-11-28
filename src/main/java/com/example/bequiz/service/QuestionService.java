package com.example.bequiz.service;

import com.example.bequiz.domain.Answer;
import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.QuestionFilters;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.exception.EntityValidationException;
import com.example.bequiz.exception.ErrorCode;
import com.example.bequiz.repository.QuestionRepository;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.Difficulty;
import com.example.bequiz.validation.EntitiesValidator;
import jakarta.transaction.Transactional;
import com.example.bequiz.utils.QuestionBooleanBuilder;
import com.example.bequiz.utils.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.bequiz.utils.Constants.QUESTION;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;
    private final QuestionBooleanBuilder questionBooleanBuilder;
    private final EntitiesMapper entitiesMapper;
    private final EntitiesValidator entitiesValidator;
    private final TagProcessor tagProcessor;

    public Page<QuestionDTO> findAll(Integer itemsPerPage, Integer pageIndex, String keyword, List<String> difficulties, List<String> tagsAsString) {
        entitiesValidator.validateQuestionFilters(itemsPerPage, pageIndex, tagsAsString, difficulties);
        List<Tag> tags = getTagsFromTagsAsString(tagsAsString);
        List<Difficulty> enumDifficulties = difficulties.stream().map(difficulty -> Difficulty.valueOf(difficulty.toUpperCase())).toList();

        QuestionFilters questionFilters = QuestionFilters.builder()
                .itemsPerPage(itemsPerPage)
                .pageIndex(pageIndex)
                .keyword(keyword)
                .difficulties(enumDifficulties)
                .tags(tags).build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        if (questionFilters.getItemsPerPage() != null && questionFilters.getPageIndex() != null)
            pageRequest = PageRequest.of(questionFilters.getPageIndex(), questionFilters.getItemsPerPage());

        Sort defaultSort = Sort.by(Sort.Direction.DESC, "dateLastModified", "dateCreated");

        return questionRepository
                .findAll(questionBooleanBuilder.buildBooleanFromQuestionFilters(questionFilters),
                        pageRequest.withSort(defaultSort))
                .map(entitiesMapper::questionToQuestionDTO);
    }

    public List<Tag> getTagsFromTagsAsString(List<String> tagsAsString) {
        if (tagsAsString == null)
            return null;

        return tagsAsString.stream()
                .map(tagTitle -> tagRepository.findOptionalByTagTitleIgnoreCase(tagTitle)
                        .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, "Tag " + tagTitle)))
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionDTO createQuestion(CreateQuestionDTO questionDTO) {
        entitiesValidator.validateCreateQuestionDTO(questionDTO);
        Difficulty difficulty = Difficulty.valueOf(questionDTO.getDifficulty().toUpperCase());
        List<Answer> answersList = questionDTO.getAnswers().stream().map(entitiesMapper::createAnswerDTOToAnswer).toList();
        Question question = Question.builder()
                .difficulty(difficulty)
                .questionBody(questionDTO.getQuestionBody())
                .questionTitle(questionDTO.getQuestionTitle())
                .answers(answersList)
                .tags(tagProcessor.processTags(questionDTO.getTags()))
                .isDeleted(false)
                .build();

        answersList.forEach(answer -> answer.setQuestion(question));

        return entitiesMapper.questionToQuestionDTO(questionRepository.save(question));
    }

    public QuestionDTO getQuestionById(UUID questionId) {
        Question question = findQuestionById(questionId);
        return entitiesMapper.questionToQuestionDTO(question);
    }

    @Transactional
    public void editQuestion(UUID uuid, CreateQuestionDTO createQuestionDTO) {
        Question question = findQuestionById(uuid);
        List<Answer> answers = createQuestionDTO.getAnswers().stream().map(answerDTO -> new Answer(answerDTO.getAnswerContent(), answerDTO.isCorrectAnswer(), question)).collect(Collectors.toList());
        entitiesValidator.validateUpdateQuestionDTO(createQuestionDTO);
        question.setAnswers(answers);
        question.setDifficulty(Difficulty.valueOf(createQuestionDTO.getDifficulty()));
        question.setQuestionTitle(createQuestionDTO.getQuestionTitle());
        question.setQuestionBody(createQuestionDTO.getQuestionBody());
        question.setTags(tagProcessor.processTags(createQuestionDTO.getTags()));

        questionRepository.save(question);
    }

    public Question findQuestionById(UUID id) {
        return questionRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, QUESTION));
    }

    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question question = findQuestionById(questionId);
        question.setDeleted(true);
        questionRepository.save(question);
    }
}
