package com.bara.JobOffers.infrastructure.login_register.controller.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String username,
        String token
) {
}
