package com.nikhil.trello.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank
        @Size(max = 20)
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 5, message = "Password must be at least 5 characters")
        String password
) {
}
