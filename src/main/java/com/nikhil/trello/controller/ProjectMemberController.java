package com.nikhil.trello.controller;

import com.nikhil.trello.dto.CreateProjectMemberRequest;
import com.nikhil.trello.dto.ProjectMemberResponse;
import com.nikhil.trello.entity.ProjectMember;
import com.nikhil.trello.exception.ResourceNotFoundException;
import com.nikhil.trello.service.ProjectMemberService;
import com.nikhil.trello.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;
    private final ProjectService projectService;

    @PostMapping("/projects/{projectId}/members")
    public ResponseEntity<ProjectMemberResponse>createProjectMember(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal UUID authenticatedUserId,
            @Valid @RequestBody CreateProjectMemberRequest request
            ){
        if(!projectService.projectBelongsToUserId(projectId, authenticatedUserId)){
           throw new ResourceNotFoundException("Project not found");
        }

        ProjectMember member = projectMemberService.addMember(authenticatedUserId, projectId, request.userId(), request.role());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(member));
    }

    private ProjectMemberResponse toResponse(ProjectMember member){
        return new ProjectMemberResponse(
                member.getId(),
                member.getProject().getId(),
                member.getUser().getId(),
                member.getRole(),
                member.getCreatedAt()
        );
    }

}
