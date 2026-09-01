package com.nikhil.trello.dto;

import java.util.UUID;

public record SignUpResponse(
        UUID id,
        String name,
        String email,
        String accessToken
) {
}
