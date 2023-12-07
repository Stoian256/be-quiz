package com.example.bequiz.utils;

public class Constants {
    public static final String QUESTION_TITLE = "QuestionTitle";
    public static final String DIFFICULTY = "Difficulty";
    public static final String ANSWERS = "Answer List";
    public static final String ANSWER = "Answer";
    public static final String PREFIX = "Prefix";
    public static final String TAG_LIST = "Tag List";
    public static final String QUESTION = "Question";
    public static final String QUIZ = "Quiz";
    public static final String ATTEMPT = "Attempt";
    public static final String QUIZ_TITLE = "Quiz Title";
    public static final String QUIZ_TIME_LIMIT = "Quiz Time Limit";
    public static final String CREATE_QUESTION_BAD_REQUEST_MESSAGES =
            """
                    Question title can not be null or empty!\n
                    Question difficulty can not be null!\n
                    Difficulty must be EASY, MEDIUM, HARD!\n
                    You must assign at least 2 answers to a question!\n
                    You are allowed to assign maximum 7 tags to a question!\n
                    You must assign at least 1 correct answers to a question!\n
                    """;
    public static final String INVALID_RETRIEVE_PARAMS_MESSAGES =
            """
                    Items per page must be grater then 0!\n
                    Page index must be greater than or equal to 0!\n
                    You are allowed to search with maximum 7 tags!\n
                    """;
    public static final String UPDATE_QUESTION_BAD_REQUEST_MESSAGES =
            """
                    You must assign at least 2 answers to a question!\n
                    You must assign at least 1 correct answers to a question!\n
                    """;
    public static final String ATTEMPT_QUESTION_ANSWER_NOT_FOUND =
            """
                    Attempt not found!\n
                    Question not found!\n
                    Answer not found!\n
                    """;
}
