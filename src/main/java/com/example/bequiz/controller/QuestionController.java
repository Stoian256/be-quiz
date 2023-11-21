package com.example.bequiz.controller;

import com.example.bequiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;


    @DeleteMapping("/deleteQuestion/{questionId}")
    public void deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
    }

}
