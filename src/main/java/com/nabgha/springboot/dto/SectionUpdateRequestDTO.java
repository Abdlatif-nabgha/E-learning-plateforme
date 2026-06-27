package com.nabgha.springboot.dto;

public record SectionUpdateRequestDTO(
        String name,         // nullable → update partial
        Integer sectionOrder // nullable → update partial
) {}

