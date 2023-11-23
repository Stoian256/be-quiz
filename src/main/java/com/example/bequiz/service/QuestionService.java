package com.example.bequiz.service;

import com.example.bequiz.domain.Answer;
import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.QuestionFilters;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.repository.QuestionRepository;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.Difficulty;
import jakarta.transaction.Transactional;
import com.example.bequiz.utils.QuestionBooleanBuilder;
import com.example.bequiz.utils.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

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
    public QuestionDTO createQuestion(CreateQuestionDTO questionDTO) {
        Difficulty difficulty = Difficulty.valueOf(questionDTO.getDifficulty().toUpperCase());
        List<Answer> answersList = questionDTO.getAnswers();
        Question question = Question.builder()
                .difficulty(difficulty)
                .questionBody(questionDTO.getQuestionBody())
                .questionTitle(questionDTO.getQuestionTitle())
                .answers(answersList)
                .tags(processTags(questionDTO.getTags()))
                .isDeleted(false)
                .build();

        answersList.forEach(answer -> answer.setQuestion(question));

      return entitiesMapper.questionToQuestionDTO(questionRepository.save(question));
    }
    @Transactional
    public void deleteQuestion(UUID id) {
        Question questionToBeDeleted = questionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Question doesn't exist"));
        questionToBeDeleted.setDeleted(true);
        questionRepository.save(questionToBeDeleted);
    }
}






