package com.example.bequiz.service;

import com.example.bequiz.domain.QuestionFilters;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.repository.QuestionRepository;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.Difficulty;
import jakarta.transaction.Transactional;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.Difficulty;
import com.example.bequiz.utils.QuestionBooleanBuilder;
import com.example.bequiz.utils.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;
    private final QuestionBooleanBuilder questionBooleanBuilder;
    private final EntitiesMapper entitiesMapper;

    public Page<QuestionDTO> findAll(Integer itemsPerPage, Integer pageIndex, String keyword, Difficulty difficulty, List<String> tagsAsString) {
        List<Tag> tags = tagRepository.findByTagTitleIn(tagsAsString);
        QuestionFilters questionFilters = QuestionFilters.builder()
                .itemsPerPage(itemsPerPage)
                .pageIndex(pageIndex)
                .keyword(keyword)
                .difficulty(difficulty)
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
    private final TagRepository tagRepository;



    @Transactional
    public List<Tag> processTags(List<String> tagList) {
        return tagList.stream()
                .map(string -> {
                    String trimmedString = string.trim();
                    if (trimmedString.length() > 1) {
                        return trimmedString.substring(0, 1).toUpperCase() + trimmedString.substring(1).toLowerCase();
                    } else {
                        return trimmedString;
                    }
                })
                .map(tagTitle -> {
                    Tag existingTag = tagRepository.findByTagTitle(tagTitle);
                    if (existingTag == null) {
                        existingTag = new Tag(tagTitle, null);
                        tagRepository.save(existingTag);
                    }
                    return existingTag;
                }).collect(Collectors.toList());
    }

    @Transactional
    public void createQuestion(CreateQuestionDTO questionDTO) {
        Difficulty difficulty = Difficulty.valueOf(questionDTO.getDifficultly());
        List<Answer> answersList = questionDTO.getAnswers();
        Question question = Question.builder()
                .difficultly(difficulty)
                .questionBody(questionDTO.getQuestionBody())
                .answers(answersList)
                .tags(processTags(questionDTO.getTags()))
                .isDeleted(false)
                .build();

        answersList.forEach(answer -> answer.setQuestion(question));

        questionRepository.save(question);
    }
}


