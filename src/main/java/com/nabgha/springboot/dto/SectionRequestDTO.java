package com.nabgha.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SectionRequestDTO(
        @NotBlank(message = "Section name is required")
        String name,

        @NotNull(message = "Section order is required")
        Integer sectionOrder
){}
