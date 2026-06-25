package com.nabgha.springboot.service.serviceImpl;

import com.nabgha.springboot.dto.TutorRequestDTO;
import com.nabgha.springboot.dto.TutorResponseDTO;
import com.nabgha.springboot.dto.TutorUpdateRequestDTO;
import com.nabgha.springboot.mapper.TutorMapper;
import com.nabgha.springboot.models.Tutor;
import com.nabgha.springboot.repository.TutorRepository;
import com.nabgha.springboot.service.TutorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;
    private final TutorMapper tutorMapper;


    @Override
    @Transactional
    public TutorResponseDTO createTutor(TutorRequestDTO tutorRequestDTO) {
        //  Validate unique email before attempting insertion
        if (tutorRepository.existsByEmail(tutorRequestDTO.email())) {
            throw new IllegalArgumentException("A tutor with this email already exists");
        }

        //  Map the TutorRequestDTO to a Tutor object
        Tutor tutor = tutorMapper.toEntity(tutorRequestDTO);
        Tutor savedTutor = tutorRepository.save(tutor);

        return tutorMapper.toDTO(savedTutor);
    }

    @Override
    public TutorResponseDTO getTutorById(Integer id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with id: "+ id));
        return tutorMapper.toDTO(tutor);
    }

    @Override
    public TutorResponseDTO getTutorByEmail(String email) {
        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with email: "+ email));
        return tutorMapper.toDTO(tutor);
    }

    @Override
    public Page<TutorResponseDTO> getAllTutors(Pageable pageable) {
        return tutorRepository.findAll(pageable)
                .map(tutorMapper::toDTO);
    }

    @Override
    @Transactional
    public TutorResponseDTO updateTutor(Integer id, TutorUpdateRequestDTO tutorRequestDTO) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor not found with id: "+ id));

        if (tutorRequestDTO.firstName() != null) {
            tutor.setFirstName(tutorRequestDTO.firstName());
        }
        if (tutorRequestDTO.lastName() != null) {
            tutor.setLastName(tutorRequestDTO.lastName());
        }
        // Update other fields as needed
        return tutorMapper.toDTO(tutor);
    }

    @Override
    @Transactional
    public void deleteTutor(Integer id) {
        if (!tutorRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete: Tutor not found with id: " + id);
        }
        tutorRepository.deleteById(id);
    }
}
