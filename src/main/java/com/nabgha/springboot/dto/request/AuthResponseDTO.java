package com.nabgha.springboot.dto.request;


public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {
}
