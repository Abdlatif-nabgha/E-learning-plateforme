package com.nabgha.springboot.dto;


import java.util.Set;

public record CourseRequestDTO(
        String title,
        String description,
        Set<Integer> tutorIds
) {
}
