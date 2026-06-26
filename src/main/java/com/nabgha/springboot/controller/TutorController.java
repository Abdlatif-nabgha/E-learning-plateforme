package com.nabgha.springboot.controller;

import com.nabgha.springboot.dto.TutorRequestDTO;
import com.nabgha.springboot.dto.TutorResponseDTO;
import com.nabgha.springboot.dto.TutorUpdateRequestDTO;
import com.nabgha.springboot.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tutors")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @PostMapping
    public ResponseEntity<TutorResponseDTO> createTutor(
            @Valid @RequestBody TutorRequestDTO requestDTO
    ) {
        // 1. Create a new Tutor entity from the request DTO
        TutorResponseDTO createdTutor = tutorService.createTutor(requestDTO);

        // 2. Build the URI for the newly created resource (e.g., /api/v1/tutors/5)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTutor.id())
                .toUri();

        // 3. Return a 201 Created response with the location header
        return ResponseEntity.created(location).body(createdTutor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> getTutorById(@PathVariable Integer id) {
        return ResponseEntity.ok(tutorService.getTutorById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TutorResponseDTO> getTutorByEmail(@PathVariable String email) {
        return ResponseEntity.ok(tutorService.getTutorByEmail(email));
    }

    @GetMapping
    public ResponseEntity<Page<TutorResponseDTO>> getAllTutors(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(tutorService.getAllTutors(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> updateTutor(
            @PathVariable Integer id,
            @RequestBody TutorUpdateRequestDTO requestDTO,
            @RequestParam Integer requestingTutorId
    ) {
        return  ResponseEntity.ok(tutorService.updateTutor(id, requestDTO, requestingTutorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(
            @PathVariable Integer id,
            @RequestParam Integer requestingTutorId
    ) {
        tutorService.deleteTutor(id, requestingTutorId);
        return ResponseEntity.noContent().build();
    }
}
