package com.nabgha.springboot.repository;

import com.nabgha.springboot.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByTitleIgnoreCase(String title);
}