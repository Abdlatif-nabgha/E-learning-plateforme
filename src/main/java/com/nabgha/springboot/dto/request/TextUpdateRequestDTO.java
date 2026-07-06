package com.nabgha.springboot.dto.request;


public record TextUpdateRequestDTO(
        String name,
        Integer size,
        String url,
        String content
) {}