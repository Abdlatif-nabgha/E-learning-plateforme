package com.nabgha.springboot.service.serviceImpl;

import com.nabgha.springboot.dto.request.FileRequestDTO;
import com.nabgha.springboot.dto.request.FileUpdateRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.mapper.FileMapper;
import com.nabgha.springboot.models.File;
import com.nabgha.springboot.models.Lecture;
import com.nabgha.springboot.repository.LectureRepository;
import com.nabgha.springboot.repository.ResourceRepository;
import com.nabgha.springboot.repository.TutorRepository;
import com.nabgha.springboot.service.FileService;
import com.nabgha.springboot.utils.OwnershipValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


    private final LectureRepository lectureRepository;
    private final TutorRepository tutorRepository;
    private final OwnershipValidator ownershipValidator;
    private final FileMapper fileMapper;
    private final ResourceRepository resourceRepository;

    @Override
    @Transactional
    public ResourceResponseDTO addFile(FileRequestDTO request, Integer lectureId, Integer tutorId) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Lecture with id not found : "+lectureId));

        if (!tutorRepository.existsById(tutorId)) {
            throw new EntityNotFoundException("Tutor not found with id " + tutorId);
        }

        ownershipValidator.verifyCourseOwnership(lecture.getSection().getCourse(), tutorId);

        if (lecture.getResource() != null) {
            throw new IllegalStateException("This lecture already has a resource with id " + lecture.getResource().getId());
        }

        File file = fileMapper.toEntity(request);

        file.setLecture(lecture);
        lecture.setResource(file);

        File savedFile = resourceRepository.save(file);

        return fileMapper.toDTO(savedFile);
    }

    @Override
    @Transactional
    public ResourceResponseDTO updateFile(FileUpdateRequestDTO request, Integer fileId, Integer tutorId) {
        File file = resourceRepository.findFileById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("file not found with id: "+ fileId));

        ownershipValidator.verifyCourseOwnership(file.getLecture().getSection().getCourse(), tutorId);

        if (request.name() != null) file.setName(request.name());
        if (request.url() != null) file.setUrl(request.url());
        if (request.fileType() != null) file.setType(request.fileType());
        if (request.size() != null) file.setSize(request.size());

        return fileMapper.toDTO(file);
    }

    @Override
    @Transactional
    public void deleteFile(Integer fileId, Integer tutorId) {

        File file = resourceRepository.findFileById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("file not found with id: "+ fileId));

        ownershipValidator.verifyCourseOwnership(file.getLecture().getSection().getCourse(), tutorId);
        resourceRepository.delete(file);
    }
}
