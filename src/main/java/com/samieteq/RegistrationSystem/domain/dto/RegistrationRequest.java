package com.samieteq.RegistrationSystem.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(
        @NotBlank(message = "full name required")
        String fullName,
        @NotBlank(message = "email required")
        @Email(message = "invalid email")
        String email,
        @NotBlank(message = "password required")
        String password
) {
}

