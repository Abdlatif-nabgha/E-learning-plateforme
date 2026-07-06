package com.nabgha.springboot.service;


import com.nabgha.springboot.dto.request.TextRequestDTO;
import com.nabgha.springboot.dto.request.TextUpdateRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.models.Resource;

public interface TextService {
    ResourceResponseDTO addText(TextRequestDTO request, Integer lectureId, Integer tutorId);
    ResourceResponseDTO updateText(TextUpdateRequestDTO request, Integer textId, Integer tutorId);
    void deleteText(Integer textId, Integer tutorId);
}