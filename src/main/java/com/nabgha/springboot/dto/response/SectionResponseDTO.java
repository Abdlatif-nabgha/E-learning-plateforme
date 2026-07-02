package com.nabgha.springboot.dto.response;

public record SectionResponseDTO(
        Integer id,
        String name,
        Integer sectionOrder,
        Integer courseId
) {}