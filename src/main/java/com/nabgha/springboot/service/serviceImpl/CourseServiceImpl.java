package com.nabgha.springboot.service.serviceImpl;

import com.nabgha.springboot.dto.CourseRequestDTO;
import com.nabgha.springboot.dto.CourseResponseDTO;
import com.nabgha.springboot.dto.CourseUpdateRequestDTO;
import com.nabgha.springboot.exception.UnauthorizedOperationException;
import com.nabgha.springboot.mapper.CourseMapper;
import com.nabgha.springboot.models.Course;
import com.nabgha.springboot.models.Tutor;
import com.nabgha.springboot.repository.CourseRepository;
import com.nabgha.springboot.repository.TutorRepository;
import com.nabgha.springboot.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final TutorRepository tutorRepository;

    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO, Integer tutorId) {

        // 1. Fetch the tutor who is creating this course from the database
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with id: "+ tutorId));

        // 2. Map the incoming CourseRequestDTO into a new Course entity
        Course course = courseMapper.toEntity(courseRequestDTO);

        // 3. Associate the fetched tutor with this new course
        course.addTutor(tutor);

        // 4. Save the newly created course (along with its relationship to the tutor) to the database
        Course savedCourse = courseRepository.save(course);

        // 5. Map the saved Course entity back to a CourseResponseDTO and return it
        return courseMapper.toDTO(savedCourse);
    }

    @Override
    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toDTO);
    }

    @Override
    public CourseResponseDTO getCourseById(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: "+ courseId));
        return courseMapper.toDTO(course);
    }

    @Override
    @Transactional
    public CourseResponseDTO updateCourse(Integer courseId, CourseUpdateRequestDTO dto, Integer tutorId) {
        // 1. Fetch the course from the database
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: "+ courseId));
        // 2. Verify that the requesting tutor is one of the tutors associated with this course
        verifyOwnership(course, tutorId);

        // 3. Update the course with the incoming DTO data
        if (dto.title() != null) {
            course.setTitle(dto.title());
        }
        if (dto.description() != null) {
            course.setDescription(dto.description());
        }

        // 4. Save the updated course back to the database
        return courseMapper.toDTO(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Integer courseId, Integer tutorId) {
        // 1. Fetch the course from the db
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: "+ courseId));

        // 2. Verify that the requesting tutor is one of the tutors associated with this course
        verifyOwnership(course, tutorId);

        // 3. Delete the course from the database
        courseRepository.delete(course);
    }

    @Override
    @Transactional
    public CourseResponseDTO addTutorToCourse(Integer courseId, Integer newTutorId, Integer requestingTutorId) {
        //1. Fetch the course and the new tutor from the database
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: "+ courseId));

        // 2. Fetch the tutor who is creating this course from the database
        Tutor newTutor = tutorRepository.findById(newTutorId)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with id: "+ newTutorId));

        // 3. Verify that the requesting tutor is one of the tutors associated with this course
        verifyOwnership(course, requestingTutorId);

        // 4. Verify that the new tutor is not already associated with this course
        boolean alreadyIn = course.getTutors().stream()
                        .anyMatch(tutor -> tutor.getId().equals(newTutorId));
        if (alreadyIn) {
            throw new IllegalStateException(
                    "This tutor is already associated with this course"
            );
        }
        // 5. Add the new tutor to the course
        course.addTutor(newTutor);
        return courseMapper.toDTO(course);
    }

    @Override
    @Transactional
    public void removeTutorFromCourse(Integer courseId, Integer requestingTutorId) {
        // 1. Fetch the course from the database
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: "+ courseId));
        // 2. Fetch the tutor who is creating this course from the database
        Tutor tutor = tutorRepository.findById(requestingTutorId)
                        .orElseThrow(() -> new EntityNotFoundException("Tutor not found with id: "+ requestingTutorId));

        // 3. Verify that the requesting tutor is one of the tutors associated with this course
        verifyOwnership(course, requestingTutorId);

        // 4. Remove the tutor from the course
        if (course.getTutors().size() == 1) {
            throw new IllegalStateException(
                    "You are the last tutor of this course. Delete the course instead."
            );
        }
        course.removeTutor(tutor);
    }

    // =================== PRIVATE HELPERS ===================
    private void verifyOwnership(Course course, Integer tutorId) {
        boolean isTutor = course.getTutors().stream()
                .anyMatch(tutor -> tutor.getId().equals(tutorId));
        if (!isTutor) {
            throw new UnauthorizedOperationException(
                    "You don't have permission to modify this course"
            );
        }
    }
}
