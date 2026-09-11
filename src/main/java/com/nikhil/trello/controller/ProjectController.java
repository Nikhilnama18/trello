package com.nikhil.trello.controller;

import com.nikhil.trello.dto.CreateProjectRequest;
import com.nikhil.trello.dto.PageResponse;
import com.nikhil.trello.dto.ProjectResponse;
import com.nikhil.trello.dto.UpdateProjectRequest;
import com.nikhil.trello.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/users/{userId}/projects")
    public ResponseEntity<ProjectResponse> createProject(
            @PathVariable UUID userId,
            @AuthenticationPrincipal UUID authenticatedUserId,
            @Valid @RequestBody CreateProjectRequest request
            ){

        if(!userId.equals(authenticatedUserId)){
            throw  new AccessDeniedException("Cannot create project for another user");
        }

        ProjectResponse response = projectService.createProject(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{userId}/projects")
    public ResponseEntity<PageResponse<ProjectResponse>> getProjectsForAUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @PathVariable UUID userId,
            @AuthenticationPrincipal UUID authenticatedUserId

    ){
        if(!userId.equals(authenticatedUserId)){
            throw  new AccessDeniedException("Cannot create project for another user");
        }

        return ResponseEntity.ok(
                projectService.getProjectsForAUser(userId, page, pageSize)
        );
    }

    @GetMapping("/users/{userId}/projects/{projectId}")
    public ResponseEntity<ProjectResponse>getProjectByIdAndUserId(
            @PathVariable UUID userId,
            @PathVariable UUID projectId,
            @AuthenticationPrincipal UUID authenticatedUserId
    ){
        if(!userId.equals(authenticatedUserId)){
            throw  new AccessDeniedException("Cannot create project for another user");
        }

        return ResponseEntity.ok(
                projectService.getProjectByIdAndUserId(projectId, userId)
        );
    }

    @PatchMapping("/users/{userId}/projects/{projectId}")
    public ResponseEntity<ProjectResponse> updateProjectByIdAndUserId(
            @PathVariable UUID userId,
            @PathVariable UUID projectId,
            @AuthenticationPrincipal UUID authenticatedUserId,
            @Valid @RequestBody UpdateProjectRequest request
    ){
        if(!userId.equals(authenticatedUserId)){
            throw  new AccessDeniedException("Cannot create project for another user");
        }

        ProjectResponse response = projectService.updateProjectName(projectId, userId, request.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
