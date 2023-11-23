package com.example.bequiz.controller;

import com.example.bequiz.dto.CreateQuestionDTO;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.service.QuestionService;
import com.example.bequiz.utils.Difficulty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class QuestionController {

    private final QuestionService questionService;

    @Operation(responses = {@ApiResponse(responseCode = "400", description = CREATE_QUESTION_BAD_REQUEST_MESSAGES, content = @Content)})
    @PostMapping("/createQuestion")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO) {
        return questionService.createQuestion(createQuestionDTO);
    }

    @Operation(responses = {@ApiResponse(responseCode = "400", description = RETRIEVE_QUESTIONS_BAD_REQUEST_MESSAGES, content = @Content)})
    @GetMapping
    public Page<QuestionDTO> findAll(@RequestParam(required = false) Integer itemsPerPage, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false) String keyword, @RequestParam(required = false) Difficulty difficulty, @RequestParam(required = false) List<String> tags) {
        return questionService.findAll(itemsPerPage, pageIndex, keyword, difficulty, tags);
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable("id") UUID questionId) {
        return questionService.getQuestionById(questionId);
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable("id") UUID questionId) {
        return questionService.getQuestionById(questionId);
    }

    @Operation(responses = {@ApiResponse(responseCode = "404", description = "Question not found!", content = @Content)})
    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable("id") UUID questionId) {
        return questionService.getQuestionById(questionId);
    }

    @DeleteMapping("/deleteQuestion/{questionId}")
    public void deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
    }


    @PutMapping("/updateQuestion{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateQuestion(@PathVariable UUID id,@RequestBody CreateQuestionDTO createQuestionDTO){
       questionService.editQuestion(id,createQuestionDTO);
    }
}
