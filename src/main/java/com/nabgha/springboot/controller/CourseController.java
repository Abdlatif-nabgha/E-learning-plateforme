package com.nabgha.springboot.controller;

import com.nabgha.springboot.dto.request.CourseRequestDTO;
import com.nabgha.springboot.dto.response.CourseResponseDTO;
import com.nabgha.springboot.dto.request.CourseUpdateRequestDTO;
import com.nabgha.springboot.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(
          @Valid @RequestBody CourseRequestDTO request,
          @RequestParam Integer tutorId
    ){
        // 1. Create a new Course entity from the request DTO
        CourseResponseDTO course = courseService.createCourse(request, tutorId);

        // 2. Build the URI for the newly created resource (e.g., /api/v1/courses/5)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.id())
                .toUri();

        return ResponseEntity.created(location).body(course);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Integer id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Integer id,
            @Valid @RequestBody CourseUpdateRequestDTO dto,
            @RequestParam Integer tutorId
    )
    {
            return ResponseEntity.ok(courseService.updateCourse(id, dto, tutorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Integer id,
            @RequestParam Integer tutorId)
    {
        courseService.deleteCourse(id, tutorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{courseId}/tutors/{newTutorEmail}")
    public ResponseEntity<CourseResponseDTO> addTutorToCourse(
            @PathVariable Integer courseId,
            @PathVariable String newTutorEmail,
            @RequestParam Integer requestingTutorId
    ) {
        return ResponseEntity
                .ok(courseService.addTutorToCourse(courseId, newTutorEmail, requestingTutorId));
    }

    @DeleteMapping("/{courseId}/tutors")
    public ResponseEntity<Void> removeTutorFromCourse(
            @PathVariable Integer courseId,
            @RequestParam Integer requestingTutorId
    ){
        courseService.removeTutorFromCourse(courseId, requestingTutorId);
        return ResponseEntity.noContent().build();
    }
}
