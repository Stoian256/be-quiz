package com.example.bequiz.controller;

import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.List;
import static com.example.bequiz.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
@PreAuthorize("hasAuthority('Admin')")
@SecurityRequirement(name = "be_quiz_auth")
public class QuestionController {

    private final QuestionService questionService;

    @Operation(responses = {@ApiResponse(responseCode = "400", description = CREATE_QUESTION_BAD_REQUEST_MESSAGES, content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public QuestionDTO createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO) {
        return questionService.createQuestion(createQuestionDTO);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = INVALID_RETRIEVE_PARAMS_MESSAGES, content = @Content)
            })
    @GetMapping
    public Page<QuestionDTO> findAll(@RequestParam(required = false) Integer itemsPerPage, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false) String keyword, @RequestParam(required = false) List<String> difficulties, @RequestParam(required = false) List<String> tags) {
        return questionService.findAll(itemsPerPage, pageIndex, keyword, difficulties, tags);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Question not found!", content = @Content)
            })
    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable("id") UUID questionId) {
        return questionService.getQuestionById(questionId);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Question not found!", content = @Content)
            })
    @DeleteMapping("/delete/{questionId}")
    public void deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Question not found!", content = @Content),
                    @ApiResponse(responseCode = "400", description = UPDATE_QUESTION_BAD_REQUEST_MESSAGES, content = @Content)
            })
    @PutMapping("/update/{id}")
    public void updateQuestion(@PathVariable UUID id, @RequestBody CreateQuestionDTO createQuestionDTO) {
        questionService.editQuestion(id, createQuestionDTO);
    }

    @GetMapping("/find-by-ids")
    public List<QuestionDTO> getQuestionsByIds(@RequestParam List<UUID> ids) {
        return questionService.getQuestionsByIds(ids);
    }
}
