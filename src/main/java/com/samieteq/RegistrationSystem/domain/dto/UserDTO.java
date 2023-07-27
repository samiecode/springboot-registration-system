package com.samieteq.RegistrationSystem.domain.dto;

import java.util.List;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String email,
        List<String> roles,
        String username,
        String accessToken
) {
}
