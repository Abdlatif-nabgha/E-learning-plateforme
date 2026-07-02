package com.nabgha.springboot.dto.request;


import jakarta.validation.constraints.Size;

public record CourseUpdateRequestDTO(
        @Size(max = 150)
        String title,
        String description
) {
}
