package com.nabgha.springboot.service;


import com.nabgha.springboot.dto.request.LectureRequestDTO;
import com.nabgha.springboot.dto.request.LectureUpdateRequestDTO;
import com.nabgha.springboot.dto.response.LectureResponseDTO;

public interface LectureService {

    LectureResponseDTO createLecture(LectureRequestDTO request, Integer sectionId, Integer tutorId);
    LectureResponseDTO getLectureById(Integer id);
    LectureResponseDTO updateLecture(Integer lectureId, LectureUpdateRequestDTO request, Integer tutorId);
    void deleteLecture(Integer lectureId, Integer tutorId);
}
