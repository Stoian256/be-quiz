package com.example.bequiz.service;

import com.example.bequiz.domain.Question;
import com.example.bequiz.repository.QuestionRepository;
import com.example.bequiz.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final TagRepository tagRepository;


    @Transactional
    public void deleteQuestion(UUID id) {
        Question questionToBeDeleted = questionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Question doesn't exist"));
        questionToBeDeleted.setDeleted(true);
        questionRepository.save(questionToBeDeleted);
    }

}