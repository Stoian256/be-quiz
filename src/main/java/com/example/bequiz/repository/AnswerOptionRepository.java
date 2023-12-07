package com.example.bequiz.repository;

import com.example.bequiz.domain.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, UUID> {
}
