package com.example.bequiz.repository;

import com.example.bequiz.domain.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID>, QuerydslPredicateExecutor<Quiz> {
    @Query("SELECT q FROM Quiz q JOIN q.quizTags t WHERE t.tagTitle IN (:tags) GROUP BY q ORDER BY COUNT(t) DESC")
    Page<Quiz> findQuizzesByTagsOrderByTagMatches(@Param("tags") List<String> tags, Pageable pageable);
}
