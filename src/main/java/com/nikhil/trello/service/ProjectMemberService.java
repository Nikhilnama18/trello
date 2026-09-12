package com.nikhil.trello.service;

import com.nikhil.trello.dto.ProjectMemberResponse;
import com.nikhil.trello.entity.Project;
import com.nikhil.trello.entity.ProjectMember;
import com.nikhil.trello.entity.User;
import com.nikhil.trello.exception.ResourceNotFoundException;
import com.nikhil.trello.model.ProjectRole;
import com.nikhil.trello.repository.ProjectMemberRepository;
import com.nikhil.trello.repository.ProjectRepository;
import com.nikhil.trello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectMember createMemberForProject(Project project, User user, ProjectRole role){
        ProjectMember member = new ProjectMember(project, user, role);
        return projectMemberRepository.save(member);
    }

    public ProjectMember getMemberForProjectAndUserId(UUID projectId, UUID userId){
        return projectMemberRepository.getMemberByProjectAndUserId(projectId, userId)
                .orElseThrow(()-> new AccessDeniedException("You are not part of the project"));
    }

    public ProjectMember addMember(
            UUID authenticatedUserId,
            UUID projectId,
            UUID userToAdd,
            ProjectRole role){
        ProjectMember currentMember = this.getMemberForProjectAndUserId(projectId, authenticatedUserId);

        if(currentMember.getRole() != ProjectRole.OWNER
        && currentMember.getRole() != ProjectRole.MANAGER){
            throw new AccessDeniedException("You don't have access to add users");
        }

        Project project = projectRepository.findProjectById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project not found"));

        User user = userRepository.findById(userToAdd)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));


        ProjectMember member = this.createMemberForProject(project, user, role);

        return projectMemberRepository.save(member);
    }

}
