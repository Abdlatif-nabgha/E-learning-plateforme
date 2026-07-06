package com.nabgha.springboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FileRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name cannot exceed 255 characters")
        String name,

        @Positive(message = "Size must be positive")
        int size,

        @NotBlank(message = "URL is required")
        String url,

        @NotBlank(message = "File type is required")
        String type
) {
}
