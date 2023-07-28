package com.samieteq.RegistrationSystem.controller;

import com.samieteq.RegistrationSystem.domain.dto.RegistrationRequest;
import com.samieteq.RegistrationSystem.exception.ErrorResponse;
import com.samieteq.RegistrationSystem.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid RegistrationRequest request, BindingResult error) {

        if (error.hasErrors()){

            List<String> errors = error.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            ErrorResponse errorResponse = new ErrorResponse(errors);

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return appUserService.registerCustomer(request);
    }

    @PostMapping("activate")
    public ResponseEntity<?> activatedUser(@RequestParam("token") String token){
        return new ResponseEntity<>(appUserService.confirmToken(token), HttpStatus.OK);
    }
/*
    @PutMapping
    public ResponseEntity<?> updatedUserDetails(){

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){

    }*/
}
