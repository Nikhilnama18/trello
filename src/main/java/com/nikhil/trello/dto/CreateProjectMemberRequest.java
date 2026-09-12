package com.nikhil.trello.dto;

import com.nikhil.trello.model.ProjectRole;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateProjectMemberRequest(
        @NotNull
        UUID userId,
        @NotNull
        ProjectRole role
) {
    @AssertTrue(message = "Role must be MANAGER, COMMENTER, or VIEWER")
    public boolean isRoleAllowed() {
        return role == ProjectRole.MANAGER
                || role == ProjectRole.COMMENTER
                || role == ProjectRole.VIEWER;
    }
}
