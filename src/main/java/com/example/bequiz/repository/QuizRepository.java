package com.example.bequiz.repository;

import com.example.bequiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID>, QuerydslPredicateExecutor<Quiz> {
}
