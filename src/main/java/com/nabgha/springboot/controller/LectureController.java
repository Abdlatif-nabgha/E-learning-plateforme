package com.nabgha.springboot.controller;

import com.nabgha.springboot.dto.request.LectureRequestDTO;
import com.nabgha.springboot.dto.request.LectureUpdateRequestDTO;
import com.nabgha.springboot.dto.response.LectureResponseDTO;
import com.nabgha.springboot.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/sections/{sectionId}/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<LectureResponseDTO> createLecture(
            @RequestBody @Valid LectureRequestDTO request,
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId,
            @RequestParam Integer tutorId
    ){
        // 1. Create new lecture entity from the request
        LectureResponseDTO lecture = lectureService.createLecture(request, sectionId, tutorId);

        // 2. Build the URI for the newly created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(lecture.id())
                .toUri();

        return ResponseEntity.created(location).body(lecture);
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<LectureResponseDTO> getLecture(
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId,
            @PathVariable Integer lectureId
    ) {
        return ResponseEntity.ok(lectureService.getLectureById(lectureId));
    }

    @PutMapping("/{lectureId}")
    public ResponseEntity<LectureResponseDTO> updateLecture(
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId,
            @PathVariable Integer lectureId,
            @Valid @RequestBody LectureUpdateRequestDTO request,
            @RequestParam Integer tutorId
    ){
        return ResponseEntity.ok(lectureService.updateLecture(lectureId, request, tutorId));
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Void> deleteLecture(
            @PathVariable Integer courseId,
            @PathVariable Integer sectionId,
            @PathVariable Integer lectureId,
            @RequestParam Integer tutorId
    ) {
        lectureService.deleteLecture(lectureId, tutorId);
        return ResponseEntity.noContent().build();
    }
}
