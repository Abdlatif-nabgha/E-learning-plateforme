package com.nabgha.springboot.dto;

public record SectionResponseDTO(
        Integer id,
        String name,
        Integer sectionOrder,
        Integer courseId
) {}