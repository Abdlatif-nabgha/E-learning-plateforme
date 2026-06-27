package com.nabgha.springboot.service;


import com.nabgha.springboot.dto.SectionRequestDTO;
import com.nabgha.springboot.dto.SectionResponseDTO;
import com.nabgha.springboot.dto.SectionUpdateRequestDTO;

public interface SectionService {

    SectionResponseDTO createSection(SectionRequestDTO request, Integer courseId, Integer tutorId);
    SectionResponseDTO getSectionById(Integer sectionId);
    SectionResponseDTO updateSection(Integer sectionId, SectionUpdateRequestDTO request, Integer tutorId);
    void deleteSection(Integer sectionId, Integer tutorId);
}
