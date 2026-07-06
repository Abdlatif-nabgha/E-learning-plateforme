package com.nabgha.springboot.dto.request;

public record FileUpdateRequestDTO(
        String name,
        Integer size,
        String url,
        String fileType
) {}