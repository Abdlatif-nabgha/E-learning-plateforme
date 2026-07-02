package com.nabgha.springboot.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TutorRequestDTO(
 @NotBlank(message = "First name is mandatory") String firstName,
 @NotBlank(message = "Last name is mandatory") String lastName,
 @NotBlank(message = "Email is mandatory")
 @Email(message = "Please provide a valid email") String email
){}
