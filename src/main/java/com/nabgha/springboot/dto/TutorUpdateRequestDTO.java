package com.nabgha.springboot.dto;


import jakarta.validation.constraints.Email;

public record TutorUpdateRequestDTO(
        String firstName,
        String lastName
){}
