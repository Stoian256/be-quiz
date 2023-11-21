package com.example.bequiz.service;

import com.example.bequiz.domain.Answer;
import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.repository.QuestionRepository;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.Difficulty;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
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


