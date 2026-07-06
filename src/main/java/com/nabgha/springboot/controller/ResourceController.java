package com.nabgha.springboot.controller;


import com.nabgha.springboot.dto.request.*;
import com.nabgha.springboot.dto.response.ResourceResponseDTO;
import com.nabgha.springboot.service.FileService;
import com.nabgha.springboot.service.TextService;
import com.nabgha.springboot.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/sections/{sectionId}/lectures/{lectureId}/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final VideoService videoService;
    private final FileService fileService;
    private final TextService textService;


    // ==== Video
    @PostMapping("/video")
    public ResponseEntity<ResourceResponseDTO> addVideo(
            @Valid @RequestBody VideoRequestDTO request,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId,
            @PathVariable Integer courseId,
            @RequestParam Integer tutorId
    ){
        ResourceResponseDTO resource = videoService.addVideo(request, lectureId, tutorId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resource.id())
                .toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @PutMapping("/video/{videoId}")
    public ResponseEntity<ResourceResponseDTO> updateVideo(
            @PathVariable Integer videoId,
            @Valid @RequestBody VideoUpdateRequestDTO request,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId
    ) {
        return ResponseEntity.ok(videoService.updateVideo(videoId,request, tutorId));
    }

    @DeleteMapping("/video/{videoId}")
    public ResponseEntity<Void> deleteVideo(
            @PathVariable Integer videoId,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId) {
        videoService.deleteVideo(videoId, tutorId);
        return ResponseEntity.noContent().build();
    }

    // ==== File
    @PostMapping("/file")
    public ResponseEntity<ResourceResponseDTO> addFile(
            @Valid @RequestBody FileRequestDTO request,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId,
            @PathVariable Integer courseId,
            @RequestParam Integer tutorId
    ) {
        ResourceResponseDTO resource = fileService.addFile(request, lectureId, tutorId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resource.id())
                .toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @PutMapping("/file/{fileId}")
    public ResponseEntity<ResourceResponseDTO> updateFile(
            @PathVariable Integer fileId,
            @Valid @RequestBody FileUpdateRequestDTO request,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId
    ) {
        return ResponseEntity.ok(fileService.updateFile(request, fileId, tutorId));
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Integer fileId,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId
    ) {
        fileService.deleteFile(fileId, tutorId);
        return ResponseEntity.noContent().build();
    }

    // ==== Text
    @PostMapping("/text")
    public ResponseEntity<ResourceResponseDTO> addText(
            @Valid @RequestBody TextRequestDTO request,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId,
            @PathVariable Integer courseId,
            @RequestParam Integer tutorId
    ) {
        ResourceResponseDTO resource = textService.addText(request, lectureId, tutorId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resource.id())
                .toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @PutMapping("/text/{textId}")
    public ResponseEntity<ResourceResponseDTO> updateText(
            @PathVariable Integer textId,
            @Valid @RequestBody TextUpdateRequestDTO request,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId
    ) {
        return ResponseEntity.ok(textService.updateText(request, textId, tutorId));
    }

    @DeleteMapping("/text/{textId}")
    public ResponseEntity<Void> deleteText(
            @PathVariable Integer textId,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId,
            @PathVariable Integer lectureId,
            @PathVariable Integer sectionId
    ) {
        textService.deleteText(textId, tutorId);
        return ResponseEntity.noContent().build();
    }
}
