package com.example.bequiz.repository;


import com.example.bequiz.domain.AttemptQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttemptQuestionRepository extends JpaRepository<AttemptQuestion, UUID> {
}