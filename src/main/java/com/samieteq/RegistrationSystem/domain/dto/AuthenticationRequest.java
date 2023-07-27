package com.samieteq.RegistrationSystem.domain.dto;

public record AuthenticationRequest(
        String username,
        String password
) {
}
