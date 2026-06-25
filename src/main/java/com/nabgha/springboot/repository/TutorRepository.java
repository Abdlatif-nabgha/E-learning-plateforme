package com.nabgha.springboot.repository;

import com.nabgha.springboot.models.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Integer> {
    Optional<Tutor> findByEmail(String email);

    boolean existsByEmail(String email);
}