package com.nabgha.springboot.dto.request;


import com.nabgha.springboot.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 8, max = 26)
        String password,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Role is required")
        @Enumerated(EnumType.STRING)
        Role role
) {
}
