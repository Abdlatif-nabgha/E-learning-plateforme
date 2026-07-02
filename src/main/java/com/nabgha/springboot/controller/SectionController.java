package com.nabgha.springboot.controller;

import com.nabgha.springboot.dto.request.SectionRequestDTO;
import com.nabgha.springboot.dto.response.SectionResponseDTO;
import com.nabgha.springboot.dto.request.SectionUpdateRequestDTO;
import com.nabgha.springboot.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<SectionResponseDTO> createSection(
            @Valid @RequestBody SectionRequestDTO request,
            @PathVariable Integer courseId,
            @RequestParam Integer tutorId
    ){
        // 1. Create new section entity from the request DTO
        SectionResponseDTO section = sectionService.createSection(request, courseId, tutorId);

        // 2. Build the URI for the newly created resource (e.g., /api/v1/courses/5/sections/1)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(section.id())
                .toUri();

        return ResponseEntity.created(location).body(section);
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<SectionResponseDTO> getSectionById(
            @PathVariable Integer sectionId,
            @PathVariable Integer courseId
    ){
        return ResponseEntity.ok(sectionService.getSectionById(sectionId));
    }

    @PutMapping("/{sectionId}")
    public ResponseEntity<SectionResponseDTO> updateSection(
            @PathVariable Integer sectionId,
            @Valid @RequestBody SectionUpdateRequestDTO request,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId
    ){
        return ResponseEntity.ok(sectionService.updateSection(sectionId, request, tutorId));
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Void> deleteSection(
            @PathVariable Integer sectionId,
            @RequestParam Integer tutorId,
            @PathVariable Integer courseId
    ){
        sectionService.deleteSection(sectionId, tutorId);
        return ResponseEntity.noContent().build();
    }
}
