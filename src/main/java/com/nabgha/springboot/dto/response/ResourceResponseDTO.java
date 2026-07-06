package com.nabgha.springboot.dto.response;

/**
 * Unified DTO used for returning any type of resource (video, file, text) to the front‑end.
 * The front‑end can decide what to render based on the {@code type} field and will ignore
 * the fields that are {@code null} for a particular resource.
 */
public record ResourceResponseDTO(
        Integer id,
        String name,
        Integer size,
        String url,
        String type,      // "video", "file", "text" — le frontend sait quoi afficher
        Long length,      // Video — null si pas video
        String fileType,  // File — null si pas file
        String content    // Text — null si pas text
) {}
