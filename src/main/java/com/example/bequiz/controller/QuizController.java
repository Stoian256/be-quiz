package com.example.bequiz.controller;

import com.example.bequiz.dto.CreateQuizDTO;
import com.example.bequiz.dto.QuizDTO;
import com.example.bequiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
@PreAuthorize("hasAuthority('Admin')")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public QuizDTO createQuiz(@RequestBody CreateQuizDTO createQuizDTO){
        return quizService.createQuiz(createQuizDTO);
    }

    @DeleteMapping("/delete/{quizId}")
    public void deleteQuiz(@PathVariable UUID quizId){
        quizService.deleteQuestion(quizId);
    }
}
