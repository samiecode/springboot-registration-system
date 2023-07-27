package com.samieteq.RegistrationSystem.security.auth;

import com.samieteq.RegistrationSystem.domain.dto.AuthenticationRequest;
import com.samieteq.RegistrationSystem.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@RequestBody @Validated AuthenticationRequest request, BindingResult error) {

    if (error.hasErrors()){

      List<String> errors = error.getAllErrors()
              .stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.toList());

      ErrorResponse errorResponse = new ErrorResponse(errors);

      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }
    return authenticationService.authenticate(request);
  }
}
