package com.nabgha.springboot.dto.response;

public record LectureResponseDTO(
    Integer id,
    String name,
    Integer sectionId,
    ResourceResponseDTO resource
) {}
