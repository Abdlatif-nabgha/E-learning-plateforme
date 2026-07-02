package com.nabgha.springboot.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TutorResponseDTO(
        Integer id,
        String firstName,
        String lastName,
        String email,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
){}
