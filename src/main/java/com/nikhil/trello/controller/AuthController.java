package com.nikhil.trello.controller;

import com.nikhil.trello.dto.LoginRequest;
import com.nikhil.trello.dto.LoginResponse;
import com.nikhil.trello.dto.SignupRequest;
import com.nikhil.trello.dto.SignupResponse;
import com.nikhil.trello.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request
            ){
        SignupResponse response = authService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> logIn(
            @Valid @RequestBody LoginRequest request
            ){
        LoginResponse response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
