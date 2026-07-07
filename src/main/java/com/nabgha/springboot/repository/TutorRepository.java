package com.nabgha.springboot.repository;

import com.nabgha.springboot.models.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Integer> {

    @Query("SELECT t FROM Tutor t WHERE t.user.email = :email")
    Optional<Tutor> findUserByEmail(String email);
}