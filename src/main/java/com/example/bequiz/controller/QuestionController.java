package com.example.bequiz.controller;

import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.service.QuestionService;
import com.example.bequiz.utils.Difficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/createQuestion")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO){
        return questionService.createQuestion(createQuestionDTO);
    }

    @GetMapping
    public Page<QuestionDTO> findAll(@RequestParam(required = false) Integer itemsPerPage, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false) String keyword, @RequestParam(required = false) Difficulty difficulty, @RequestParam(required = false) List<String> tags) {
        return questionService.findAll(itemsPerPage, pageIndex, keyword, difficulty, tags);
    }


    @DeleteMapping("/deleteQuestion/{questionId}")
    public void deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
    }

}
