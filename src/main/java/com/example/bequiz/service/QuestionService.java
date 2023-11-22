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
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
    public void editQuestion(UUID uuid, CreateQuestionDTO createQuestionDTO) {
        Question question = questionRepository.findById(uuid).orElseThrow(() -> new ObjectNotFoundException(uuid, "Question not found"));
        if (!validateAnswers(createQuestionDTO.getAnswers())){
            throw new RuntimeException("There must be at least 2 answers and one of them must be correct");
        }
        question.setAnswers(createQuestionDTO.getAnswers());
        question.setDifficultly(Difficulty.valueOf(createQuestionDTO.getDifficultly()));
        question.setQuestionBody(createQuestionDTO.getQuestionBody());
        question.setTags(processTags(createQuestionDTO.getTags()));

        questionRepository.save(question);
    }

    public Question findQuestionById(UUID id) {
        return questionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Question not found! "));
    }

    public boolean validateAnswers(List<Answer> answers) {
        if (answers.size() < 2) {
            return false;
        }
        for (Answer answer : answers) {
            if (answer.isCorrectAnswer()) {
                return true;
            }
        }
        return false;
    }
}
