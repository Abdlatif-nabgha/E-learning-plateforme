package com.nabgha.springboot.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CourseRequestDTO(
        @NotBlank(message = "Course title is required")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        String title,

        String description,

        @NotEmpty(message = "A course must have at least one assigned tutor")
        Set<Integer> tutorIds
) {
}
