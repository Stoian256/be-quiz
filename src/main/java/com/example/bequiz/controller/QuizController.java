package com.example.bequiz.controller;

import com.example.bequiz.dto.CreateQuizDTO;
import com.example.bequiz.dto.QuizDTO;
import com.example.bequiz.dto.RetrieveQuizDTO;
import com.example.bequiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.bequiz.utils.Constants.INVALID_RETRIEVE_PARAMS_MESSAGES;


@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
@PreAuthorize("hasAuthority('Admin')")
@SecurityRequirement(name = "be_quiz_auth")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public QuizDTO createQuiz(@RequestBody CreateQuizDTO createQuizDTO) {
        return quizService.createQuiz(createQuizDTO);
    }

    @DeleteMapping("/delete/{quizId}")
    public void deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuestion(quizId);
    }


    @PutMapping("/update/{id}")
    public QuizDTO updateQuiz(@PathVariable UUID id, @RequestBody CreateQuizDTO createQuizDTO) {
        return quizService.updateQuiz(id, createQuizDTO);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = INVALID_RETRIEVE_PARAMS_MESSAGES, content = @Content)
            })
    @GetMapping
    public Page<RetrieveQuizDTO> findAll(@RequestParam(required = false) Integer itemsPerPage, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false) String keyword, @RequestParam(required = false) List<String> difficultyLevels, @RequestParam(required = false) List<String> tags) {
        return quizService.findAll(itemsPerPage, pageIndex, keyword, difficultyLevels, tags);
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

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = INVALID_RETRIEVE_PARAMS_MESSAGES, content = @Content)
            })
    @GetMapping("search-by-tags")
    @PreAuthorize("permitAll()")
    public Page<RetrieveQuizDTO> searchQuizzesByTags(@RequestParam(required = false) Integer itemsPerPage, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false) List<String> tags) {
        return quizService.searchQuizzesByTags(itemsPerPage, pageIndex, tags);
    }
}
