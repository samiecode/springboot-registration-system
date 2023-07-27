package com.samieteq.RegistrationSystem.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorDetail {
        private final String message;
        private final LocalDateTime timestamp;

}
