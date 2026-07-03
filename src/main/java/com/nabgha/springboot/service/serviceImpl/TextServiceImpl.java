package com.nabgha.springboot.service.serviceImpl;

import com.nabgha.springboot.dto.request.TextRequestDTO;
import com.nabgha.springboot.dto.request.TextUpdateRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.mapper.TextMapper;
import com.nabgha.springboot.models.File;
import com.nabgha.springboot.models.Lecture;
import com.nabgha.springboot.models.Text;
import com.nabgha.springboot.repository.LectureRepository;
import com.nabgha.springboot.repository.ResourceRepository;
import com.nabgha.springboot.repository.TutorRepository;
import com.nabgha.springboot.service.TextService;
import com.nabgha.springboot.utils.OwnershipValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TextServiceImpl implements TextService {


    private final LectureRepository lectureRepository;
    private final TutorRepository tutorRepository;
    private final OwnershipValidator ownershipValidator;
    private final TextMapper textMapper;
    private final ResourceRepository resourceRepository;

    @Override
    @Transactional
    public ResourceResponseDTO addText(TextRequestDTO request, Integer lectureId, Integer tutorId) {
        //
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Lecture with id not found : "+lectureId));

        if (!tutorRepository.existsById(tutorId)) {
            throw new EntityNotFoundException("Tutor not found with id " + tutorId);
        }

        ownershipValidator.verifyCourseOwnership(lecture.getSection().getCourse(), tutorId);

        if (lecture.getResource() != null) {
            throw new IllegalArgumentException("Lecture with id " + lectureId + " already has a resource");
        }

        Text text = textMapper.toEntity(request);
        text.setLecture(lecture);
        lecture.setResource(text);

        Text savedText = resourceRepository.save(text);

        return textMapper.toDTO(savedText);
    }

    @Override
    @Transactional
    public ResourceResponseDTO updateText(TextUpdateRequestDTO request, Integer textId, Integer tutorId) {
        Text text = resourceRepository.findTextById(textId)
                .orElseThrow(() -> new EntityNotFoundException("Text with id " + textId + " not found"));
        ownershipValidator.verifyCourseOwnership(text.getLecture().getSection().getCourse(), tutorId);

        if (request.name() != null) text.setName(request.name());
        if (request.url() != null) text.setUrl(request.url());
        if (request.content() != null) text.setContent(request.content());
        if (request.size() != null) text.setSize(request.size());

        return textMapper.toDTO(text); // dirty checking no need to save method
    }

    @Override
    @Transactional
    public void deleteText(Integer textId, Integer tutorId) {
        Text text = resourceRepository.findTextById(textId)
                .orElseThrow(() -> new EntityNotFoundException("text not found with id: "+ textId));

        ownershipValidator.verifyCourseOwnership(text.getLecture().getSection().getCourse(), tutorId);
        
        if (text.getLecture() != null) {
            text.getLecture().setResource(null);
        }
        
        resourceRepository.delete(text);
    }
}
