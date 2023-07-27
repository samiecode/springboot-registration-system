package com.samieteq.RegistrationSystem.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ErrorResponse {
    private List<String> errors;

    public ErrorResponse(List<String> errors) {
        this.errors = errors;
    }
}

