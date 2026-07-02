package com.nabgha.springboot.service;


import com.nabgha.springboot.dto.request.TutorRequestDTO;
import com.nabgha.springboot.dto.response.TutorResponseDTO;
import com.nabgha.springboot.dto.request.TutorUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TutorService {
    TutorResponseDTO createTutor(TutorRequestDTO tutorRequestDTO);

    TutorResponseDTO getTutorById(Integer id);

    TutorResponseDTO getTutorByEmail(String email);

    Page<TutorResponseDTO> getAllTutors(Pageable pageable);

    TutorResponseDTO updateTutor(Integer id, TutorUpdateRequestDTO dto, Integer requestingTutorId);

    void deleteTutor(Integer id, Integer requestingTutorId);
}
