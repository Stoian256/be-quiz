package com.example.bequiz.controller;

import com.example.bequiz.domain.Question;
import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;



    @PutMapping("/updateQuestion{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateQuestion(@PathVariable UUID id,@RequestBody CreateQuestionDTO createQuestionDTO){
       questionService.editQuestion(id,createQuestionDTO);
    }
}
