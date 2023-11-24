package com.example.bequiz.service;

import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Quiz;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.CreateQuizDTO;
import com.example.bequiz.repository.QuizRepository;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.Difficulty;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final TagRepository tagRepository;

    @Transactional
    public void createQuiz(CreateQuizDTO createQuizDTO){
        List<Tag> tags=processTags(createQuizDTO.getTags());
        List<Question> questions=createQuizDTO.getQuestions();
        Quiz quiz=Quiz.builder()
                .quizTitle(createQuizDTO.getQuizTitle())
                .difficultyLevel(Difficulty.valueOf(createQuizDTO.getDifficultyLevel().toUpperCase()))
                .isDeleted(false)
                .timeLimit(Duration.ofMinutes(createQuizDTO.getTimeLimit()))
                .tags(tags)
                .questions(questions)
                .build();
        questions.forEach(question -> question.getQuizzes().add(quiz));
        quizRepository.save(quiz);
    }

    @Transactional
    public List<Tag> processTags(List<String> tagList) {
        if (tagList !=null){
            return tagList.stream()
                    .map(string -> {
                        String trimmedString = string.trim();
                        if (trimmedString.length() > 1) {
                            return trimmedString.substring(0, 1).toUpperCase() + trimmedString.substring(1).toLowerCase();
                        } else {
                            return trimmedString;
                        }
                    })
                    .map(tagTitle -> {
                        Tag existingTag = tagRepository.findByTagTitle(tagTitle);
                        if (existingTag == null) {
                            existingTag = new Tag(tagTitle, null,null);
                            tagRepository.save(existingTag);
                        }
                        return existingTag;
                    }).collect(Collectors.toList());
        }
        return null;
    }
}
