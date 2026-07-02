package com.nabgha.springboot.dto.response;


import java.util.Set;

public record CourseResponseDTO(
        Integer id,
        String title,
        String description,
        Set<CourseTutorInfo> tutors
) {

    public record CourseTutorInfo(
            Integer id,
            String firstName,
            String lastName
    ) {}
}
