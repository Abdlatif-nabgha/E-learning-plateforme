package com.nabgha.springboot.service;


import com.nabgha.springboot.dto.request.FileRequestDTO;
import com.nabgha.springboot.dto.request.FileUpdateRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;

public interface FileService {

    ResourceResponseDTO addFile(FileRequestDTO fileRequestDTO, Integer lectureId, Integer tutorId);
    ResourceResponseDTO updateFile(FileUpdateRequestDTO request, Integer fileId, Integer tutorId);
    void deleteFile(Integer fileId, Integer tutorId);
}
