package com.nikhil.trello.dto;

import java.time.Instant;
import java.util.UUID;

public record LoginResponse(
        UUID id,
        String accessToken,
        Instant expiresAt
) {
}
