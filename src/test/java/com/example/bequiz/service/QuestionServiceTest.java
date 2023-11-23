package com.example.bequiz.service;

import com.example.bequiz.domain.Answer;
import com.example.bequiz.repository.QuestionRepository;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.EntitiesMapper;
import com.example.bequiz.utils.QuestionBooleanBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionServiceTest {

    private List<Answer> answerList;
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


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        answerList.clear();
    }

    @Test
    public void testValidateAnswerWithLessThan2Answers(){
        answerList=new ArrayList<>();
        assertFalse(questionService.validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWhenListIsNull(){
        assertFalse(questionService.validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWith2FalseAnswers(){
        answerList=new ArrayList<>();
        answerList.add(new Answer("lara",false,null));
        answerList.add(new Answer("tara",false,null));
        assertFalse(questionService.validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWithOnly1TrueAnswers(){
        answerList=new ArrayList<>();
        answerList.add(new Answer("tara",true,null));
        assertFalse(questionService.validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWith2AnswersWhenOneIsTrue(){
        answerList=new ArrayList<>();
        answerList.add(new Answer("tara",true,null));
        answerList.add(new Answer("tara",false,null));
        assertTrue(questionService.validateAnswers(answerList));
    }


    @Test
    public void testValidateAnswerWith4AnswersWhenOneIsTrue(){
        answerList=new ArrayList<>();
        answerList.add(new Answer("Da",true,null));
        answerList.add(new Answer("Nu",false,null));
        answerList.add(new Answer("Incorrect",false,null));
        answerList.add(new Answer("Correct",false,null));
        assertTrue(questionService.validateAnswers(answerList));
    }

    @Test
    public void testValidateAnswerWith2TrueAnswers(){
        answerList=new ArrayList<>();
        answerList.add(new Answer("tara",true,null));
        answerList.add(new Answer("tara",true,null));
        assertTrue(questionService.validateAnswers(answerList));
    }
}