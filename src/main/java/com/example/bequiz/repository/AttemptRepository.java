package com.example.bequiz.repository;

import com.example.bequiz.domain.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttemptRepository extends JpaRepository<Attempt, UUID> {
}
