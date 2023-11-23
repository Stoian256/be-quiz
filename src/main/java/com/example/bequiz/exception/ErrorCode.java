package com.example.bequiz.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_FIELD(400, "%s can not be null or empty!"),
    INVALID_NUMBER_OF_TAGS(400, "You are allowed to assign maximum 7 tags to a question!"),
    INVALID_NUMBER_OF_ANSWERS(400, "You must assign at least 2 answers to a question!"),
    INVALID_NUMBER_OF_CORRECT_ANSWERS(400, "You must assign at least 1 correct answers to a question!"),
    INVALID_ITEMS_PER_PAGE(400, "Items per page must be grater then 0!"),
    INVALID_PAGE_INDEX(400, "Page index must be grater than or equal to 0!"),
    INVALID_DIFFICULTY(400, "Difficulty must be EASY, MEDIUM, HARD!");
    private final Integer code;
    private final String message;
}
