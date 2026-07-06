package com.nabgha.springboot.service.serviceImpl;

import com.nabgha.springboot.dto.request.VideoRequestDTO;
import com.nabgha.springboot.dto.request.VideoUpdateRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.mapper.VideoMapper;
import com.nabgha.springboot.models.Lecture;
import com.nabgha.springboot.models.Video;
import com.nabgha.springboot.repository.LectureRepository;
import com.nabgha.springboot.repository.ResourceRepository;
import com.nabgha.springboot.repository.TutorRepository;
import com.nabgha.springboot.service.VideoService;
import com.nabgha.springboot.utils.OwnershipValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoServiceImpl implements VideoService {


    private final LectureRepository lectureRepository;
    private final TutorRepository tutorRepository;
    private final OwnershipValidator ownershipValidator;
    private final VideoMapper videoMapper;
    private final ResourceRepository resourceRepository;

    @Override
    @Transactional
    public ResourceResponseDTO addVideo(VideoRequestDTO request, Integer lectureId, Integer tutorId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found with id " + lectureId));

        if (!tutorRepository.existsById(tutorId)) {
            throw new EntityNotFoundException("Tutor not found with id " + tutorId);
        }

        ownershipValidator.verifyCourseOwnership(lecture.getSection().getCourse(), tutorId);

        // OneToOne - one lecture per resource
        if (lecture.getResource() != null) {
            throw new IllegalStateException("This lecture already has a resource with id " + lecture.getResource().getId());
        }
        Video video = videoMapper.toEntity(request);

        // implement bidirectional relation
        video.setLecture(lecture);
        lecture.setResource(video);

        Video savedVideo = resourceRepository.save(video);

        return videoMapper.toDTO(savedVideo);
    }

    @Override
    @Transactional
    public ResourceResponseDTO updateVideo(Integer videoId, VideoUpdateRequestDTO request, Integer tutorId) {
        Video video = resourceRepository.findVideoById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found with id " + videoId));

        ownershipValidator.verifyCourseOwnership(video.getLecture().getSection().getCourse(), tutorId);

        if (request.name() != null) video.setName(request.name());
        if (request.url() != null) video.setUrl(request.url());
        if (request.length() != null) video.setLength(request.length());
        if (request.size() != null) video.setSize(request.size());

        return videoMapper.toDTO(video);
    }

    @Override
    @Transactional
    public void deleteVideo(Integer videoId, Integer tutorId) {
        Video video = resourceRepository.findVideoById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found with id: "+ videoId));

        ownershipValidator.verifyCourseOwnership(video.getLecture().getSection().getCourse(), tutorId);
        resourceRepository.delete(video);
    }
}
