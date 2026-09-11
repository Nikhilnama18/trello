package com.nikhil.trello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProjectRequest(
        @NotBlank
        @Size(min = 4)
        String name
) {
}
