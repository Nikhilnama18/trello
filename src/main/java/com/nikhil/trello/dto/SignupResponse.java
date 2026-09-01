package com.nikhil.trello.dto;

import java.util.UUID;

public record SignupResponse(
        UUID id,
        String name,
        String email,
        String accessToken
) {
}
