package com.example.bequiz.controller;

import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;



    @PostMapping("/createQuestion")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO){
        questionService.createQuestion(createQuestionDTO);
    }
}
