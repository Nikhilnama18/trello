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

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(
            @AuthenticationPrincipal UUID authenticatedUserId,
            @Valid @RequestBody CreateProjectRequest request
            ){

        ProjectResponse response = projectService.createProject(request, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/projects")
    public ResponseEntity<PageResponse<ProjectResponse>> getProjectsForAUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @AuthenticationPrincipal UUID authenticatedUserId

    ){
        return ResponseEntity.ok(
                projectService.getProjectsForAUser(authenticatedUserId, page, pageSize)
        );
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse>getProjectByIdAndUserId(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal UUID authenticatedUserId
    ){
        return ResponseEntity.ok(
                projectService.getProjectResponseByIdAndUserId(projectId, authenticatedUserId)
        );
    }

    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> updateProjectByIdAndUserId(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal UUID authenticatedUserId,
            @Valid @RequestBody UpdateProjectRequest request
    ){
        ProjectResponse response = projectService.updateProjectName(projectId, authenticatedUserId, request.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
