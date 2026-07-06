package com.nabgha.springboot.dto.request;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record VideoUpdateRequestDTO(
        @Size(max = 255, message = "Name cannot exceed 255 characters")
        String name,

        @Positive(message = "Size must be positive")
        Integer size,

        String url,

        @Positive(message = "Length must be positive")
        Long length
) {
}
