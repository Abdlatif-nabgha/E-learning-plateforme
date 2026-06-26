package com.nabgha.springboot.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequestDTO(
        @NotBlank(message = "Course title is required")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        String title,

        String description
) {
}
