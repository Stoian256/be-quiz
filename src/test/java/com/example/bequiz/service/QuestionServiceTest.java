package com.example.bequiz.service;

import com.example.bequiz.dto.CreateAnswerDTO;
import com.example.bequiz.repository.QuestionRepository;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.EntitiesMapper;
import com.example.bequiz.utils.QuestionBooleanBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionServiceTest {

    private List<CreateAnswerDTO> answerList;
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private QuestionBooleanBuilder questionBooleanBuilder;

    @Mock
    private EntitiesMapper entitiesMapper;

    @InjectMocks
    private QuestionService questionService;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @Before
    public void setUp() throws Exception {
        answerList = new ArrayList<>();
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        answerList.clear();
    }

    private boolean validateAnswers(List<CreateAnswerDTO> answers) {
        if (answers == null || answers.size() < 2) {
            return false;
        }
        for (CreateAnswerDTO answer : answers) {
            if (answer.isCorrectAnswer()) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testValidateAnswerWithLessThan2Answers() {
        assertFalse(validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWhenListIsNull() {
        assertFalse(validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWith2FalseAnswers() {
        answerList.add(new CreateAnswerDTO("lara", false));
        answerList.add(new CreateAnswerDTO("tara", false));
        assertFalse(validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWithOnly1TrueAnswers() {
        answerList.add(new CreateAnswerDTO("tara", true));
        assertFalse(validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWith2AnswersWhenOneIsTrue() {
        answerList.add(new CreateAnswerDTO("tara", true));
        answerList.add(new CreateAnswerDTO("tara", false));
        assertTrue(validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWith4AnswersWhenOneIsTrue() {
        answerList.add(new CreateAnswerDTO("Da", true));
        answerList.add(new CreateAnswerDTO("Nu", false));
        answerList.add(new CreateAnswerDTO("Incorrect", false));
        answerList.add(new CreateAnswerDTO("Correct", false));
        assertTrue(validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWith2TrueAnswers() {
        answerList.add(new CreateAnswerDTO("tara", true));
        answerList.add(new CreateAnswerDTO("tara", true));
        assertTrue(validateAnswers(answerList));
    }
}