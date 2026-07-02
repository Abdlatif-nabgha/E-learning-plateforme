package com.nabgha.springboot.service.serviceImpl;

import com.nabgha.springboot.dto.request.LectureRequestDTO;
import com.nabgha.springboot.dto.request.LectureUpdateRequestDTO;
import com.nabgha.springboot.dto.response.LectureResponseDTO;
import com.nabgha.springboot.mapper.LectureMapper;
import com.nabgha.springboot.models.Lecture;
import com.nabgha.springboot.models.Section;
import com.nabgha.springboot.repository.LectureRepository;
import com.nabgha.springboot.repository.SectionRepository;
import com.nabgha.springboot.repository.TutorRepository;
import com.nabgha.springboot.service.LectureService;
import com.nabgha.springboot.utils.OwnershipValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureServiceImpl implements LectureService {

    private final SectionRepository sectionRepository;
    private final TutorRepository tutorRepository;
    private final OwnershipValidator ownershipValidator;
    private final LectureMapper lectureMapper;
    private final LectureRepository lectureRepository;

    @Transactional
    @Override
    public LectureResponseDTO createLecture(LectureRequestDTO request, Integer sectionId, Integer tutorId) {
        // 1. Fetch section from database
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException("Section not found: " + sectionId));

        if (!tutorRepository.existsById(tutorId)) {
            throw new EntityNotFoundException("Tutor not found: " + tutorId);
        }

        // ownership check via parent course section
        ownershipValidator.verifyCourseOwnership(section.getCourse(), tutorId);

        Lecture lecture = lectureMapper.toEntity(request);
        section.addLecture(lecture);

        Lecture savedLecture = lectureRepository.save(lecture);

        return  lectureMapper.toDTO(savedLecture);
    }

    @Override
    public LectureResponseDTO getLectureById(Integer id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found: " + id));

        return lectureMapper.toDTO(lecture);
    }

    @Override
    @Transactional
    public LectureResponseDTO updateLecture(Integer lectureId, LectureUpdateRequestDTO request, Integer tutorId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found: " + lectureId));

        ownershipValidator.verifyCourseOwnership(lecture.getSection().getCourse(), tutorId);

        if (request.name() != null && !request.name().isBlank()) {
            lecture.setName(request.name());
        }

        return lectureMapper.toDTO(lecture);
    }

    @Override
    @Transactional
    public void deleteLecture(Integer lectureId, Integer tutorId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found: " + lectureId));

        ownershipValidator.verifyCourseOwnership(lecture.getSection().getCourse(), tutorId);

        lectureRepository.delete(lecture);
    }
}
