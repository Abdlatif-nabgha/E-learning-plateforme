package com.nabgha.springboot.service;


import com.nabgha.springboot.dto.request.CourseRequestDTO;
import com.nabgha.springboot.dto.response.CourseResponseDTO;
import com.nabgha.springboot.dto.request.CourseUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseResponseDTO createCourse(CourseRequestDTO course, Integer tutorId);
    Page<CourseResponseDTO> getAllCourses(Pageable pageable);
    CourseResponseDTO getCourseById(Integer id);
    CourseResponseDTO updateCourse(Integer id, CourseUpdateRequestDTO course, Integer tutorId);
    void deleteCourse(Integer id, Integer tutorId);
    CourseResponseDTO addTutorToCourse(Integer courseId, Integer newTutorId, Integer requestingTutorId);
    void removeTutorFromCourse(Integer courseId, Integer requestingTutorId);
}
