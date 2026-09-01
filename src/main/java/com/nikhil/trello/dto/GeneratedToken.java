package com.nikhil.trello.dto;

import java.time.Instant;

public record GeneratedToken(
        String accessToken,
        Instant expiration
) {
}
