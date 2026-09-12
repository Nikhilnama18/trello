package com.nikhil.trello.dto;

import com.nikhil.trello.model.ProjectRole;

import java.time.Instant;
import java.util.UUID;

public record ProjectMemberResponse(
        UUID id,
        UUID projectId,
        UUID userId,
        ProjectRole role,
        Instant createdAt
) {
}
