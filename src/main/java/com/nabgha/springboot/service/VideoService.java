package com.nabgha.springboot.service;


import com.nabgha.springboot.dto.request.VideoRequestDTO;
import com.nabgha.springboot.dto.request.VideoUpdateRequestDTO;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;

public interface VideoService {

    ResourceResponseDTO addVideo(VideoRequestDTO request, Integer lectureId, Integer tutorId);
    ResourceResponseDTO updateVideo(Integer videoId, VideoUpdateRequestDTO request, Integer tutorId);
    void deleteVideo(Integer videoId, Integer tutorId);
}
