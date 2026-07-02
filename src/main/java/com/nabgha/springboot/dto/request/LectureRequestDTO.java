package com.nabgha.springboot.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LectureRequestDTO(
    @NotBlank(message = "Lecture name is required")
    @Size(max = 150, message = "Lecture name cannot exceed 150 characters")
    String name
) {}
