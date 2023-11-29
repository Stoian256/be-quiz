package com.example.bequiz.controller;

import com.example.bequiz.dto.CreateQuizDTO;
import com.example.bequiz.dto.QuizDTO;
import com.example.bequiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
@PreAuthorize("hasAuthority('Admin')")
@SecurityRequirement(name = "be_quiz_auth")
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

    @PutMapping("/update/{id}")
    public QuizDTO updateQuiz(@PathVariable UUID id,@RequestBody CreateQuizDTO createQuizDTO){
        return quizService.updateQuiz(id,createQuizDTO);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Quiz not found!", content = @Content)
            })
    @GetMapping("/{id}")
    public QuizDTO getQuizById(@PathVariable("id") UUID id) {
        return quizService.getQuizById(id);
    }
}
