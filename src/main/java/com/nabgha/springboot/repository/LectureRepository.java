package com.nabgha.springboot.repository;

import com.nabgha.springboot.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}