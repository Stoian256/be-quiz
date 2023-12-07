package com.example.bequiz.service;

import com.example.bequiz.repository.AnswerOptionRepository;
import com.example.bequiz.repository.AttemptQuestionRepository;
import com.example.bequiz.repository.AttemptRepository;
import com.example.bequiz.repository.QuizRepository;
import com.example.bequiz.utils.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final AttemptQuestionRepository attemptQuestionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuizRepository quizRepository;
    private final EntitiesMapper entitiesMapper;
}
