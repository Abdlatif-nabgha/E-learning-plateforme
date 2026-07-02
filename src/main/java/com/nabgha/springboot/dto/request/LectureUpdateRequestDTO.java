package com.nabgha.springboot.dto.request;


import jakarta.validation.constraints.Size;

public record LectureUpdateRequestDTO(
        @Size(min = 3, max = 150, message = "Lecture name cannot be less than 3 or more than 150")
        String name
) {
}
