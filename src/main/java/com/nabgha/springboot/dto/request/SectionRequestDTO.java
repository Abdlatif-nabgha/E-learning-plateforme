package com.nabgha.springboot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SectionRequestDTO(
        @NotBlank(message = "Section name is required")
        String name,

        @NotNull(message = "Section order is required")
        Integer sectionOrder
){}
