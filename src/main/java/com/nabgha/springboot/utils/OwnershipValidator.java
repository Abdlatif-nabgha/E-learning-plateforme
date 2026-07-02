package com.nabgha.springboot.utils;

import com.nabgha.springboot.exception.UnauthorizedOperationException;
import com.nabgha.springboot.models.Course;
import org.springframework.stereotype.Component;

@Component
public class OwnershipValidator {

    public void verifyCourseOwnership(Course course, Integer tutorId) {
        boolean isTutor = course.getTutors().stream()
                .anyMatch(t -> t.getId().equals(tutorId));
        if (!isTutor) {
            throw new UnauthorizedOperationException(
                    "You don't have permission to modify this course"
            );
        }

    }
}
