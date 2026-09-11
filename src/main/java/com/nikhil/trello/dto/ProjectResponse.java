package com.nikhil.trello.dto;

import java.time.Instant;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        UUID userId,
        Instant createdAt
) {
}
