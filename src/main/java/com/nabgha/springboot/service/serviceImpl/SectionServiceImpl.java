package com.nabgha.springboot.service.serviceImpl;

import com.nabgha.springboot.dto.request.SectionRequestDTO;
import com.nabgha.springboot.dto.response.SectionResponseDTO;
import com.nabgha.springboot.dto.request.SectionUpdateRequestDTO;
import com.nabgha.springboot.mapper.SectionMapper;
import com.nabgha.springboot.models.Course;
import com.nabgha.springboot.models.Section;
import com.nabgha.springboot.repository.CourseRepository;
import com.nabgha.springboot.repository.SectionRepository;
import com.nabgha.springboot.repository.TutorRepository;
import com.nabgha.springboot.utils.OwnershipValidator;
import com.nabgha.springboot.service.SectionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectionServiceImpl implements SectionService {

    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final SectionMapper sectionMapper;
    private final SectionRepository sectionRepository;
    private final OwnershipValidator ownershipValidator;

    @Override
    @Transactional
    public SectionResponseDTO createSection(SectionRequestDTO request, Integer courseId, Integer tutorId) {

        // 1. Fetch the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: "+ courseId));


        // 2. Check that the tutor is associated with the course
        tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with id: "+ tutorId));

        // check ownership via stream
        ownershipValidator.verifyCourseOwnership(course, tutorId);

        // 3. Map DTO to entity
        Section section = sectionMapper.toEntity(request);

        // 4. Add section to course
        course.addSection(section);

        // 5. Save section to database
        Section savedSection = sectionRepository.save(section);

        // 6. Map entity to DTO and return
        return sectionMapper.toDTO(savedSection);
    }

    @Override
    public SectionResponseDTO getSectionById(Integer sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException("Section not found with id: "+ sectionId));

        return sectionMapper.toDTO(section);
    }

    @Override
    @Transactional
    public SectionResponseDTO updateSection(Integer id, SectionUpdateRequestDTO request, Integer tutorId) {
       // 1. Fetch the section
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Section not found with id: "+ id));

        // 2. Check that the tutor is associated with the course
        ownershipValidator.verifyCourseOwnership(section.getCourse(), tutorId);

        // 3. Update section fields
        if (request.name() != null) {
            section.setName(request.name());
        }
        if (request.sectionOrder() != null) {
            section.setSectionOrder(request.sectionOrder());
        }
        return sectionMapper.toDTO(section);
    }

    @Override
    @Transactional
    public void deleteSection(Integer id, Integer tutorId) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Section not found with id: "+ id));

        ownershipValidator.verifyCourseOwnership(section.getCourse(), tutorId);

        sectionRepository.delete(section);
    }
}
