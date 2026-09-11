package com.nikhil.trello.service;

import com.nikhil.trello.dto.CreateProjectRequest;
import com.nikhil.trello.dto.PageResponse;
import com.nikhil.trello.dto.ProjectResponse;
import com.nikhil.trello.entity.Project;
import com.nikhil.trello.entity.User;
import com.nikhil.trello.exception.ResourceNotFoundException;
import com.nikhil.trello.model.ProjectRole;
import com.nikhil.trello.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberService projectMemberService;
    private final UserService userService;

    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request, UUID userId){

        User user = userService.getUserById(userId);

        Project project = new Project(request.name(), user);

        Project savedProject = projectRepository.save(project);

        projectMemberService.createProject(savedProject, user, ProjectRole.OWNER);

        return new ProjectResponse(savedProject.getId(), savedProject.getName(), user.getId(), savedProject.getCreatedAt());
    }

    public PageResponse<ProjectResponse> getProjectsForAUser(
            UUID userId,
            int page,
            int pageSize
    ){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Project> projects = projectRepository.findByCreatedById(userId,pageable);

         List<ProjectResponse> data = projects.getContent()
                 .stream()
                 .map(this::toProjectResponse)
                 .toList();

         return new PageResponse<>(
                 projects.getTotalElements(),
                 projects.getNumber(),
                 projects.getSize(),
                 projects.getTotalPages(),
                 data
         );
    }

    public ProjectResponse getProjectByIdAndUserId(
            UUID projectId,
            UUID userId
    ){
        Project project = projectRepository.findByIdAndCreatedById(projectId, userId)
                .orElseThrow(()-> new ResourceNotFoundException("Project not found"));

        return this.toProjectResponse(project);
    }

    public ProjectResponse updateProjectName(
            UUID projectId,
            UUID userId,
            String name
    ){
        Project project = projectRepository.findByIdAndCreatedById(projectId, userId)
            .orElseThrow(()-> new ResourceNotFoundException("Project not found"));

        project.updateName(name);

        Project savedProject = projectRepository.save(project);

        return this.toProjectResponse(savedProject);
    }

    private ProjectResponse toProjectResponse(Project project){
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getCreatedBy().getId(),
                project.getCreatedAt()
        );
    }
}
