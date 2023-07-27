package com.samieteq.RegistrationSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderErrorResponse {

    private String message;
    private Boolean isValid;

}
