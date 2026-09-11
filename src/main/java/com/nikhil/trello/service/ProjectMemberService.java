package com.nikhil.trello.service;

import com.nikhil.trello.entity.Project;
import com.nikhil.trello.entity.ProjectMember;
import com.nikhil.trello.entity.User;
import com.nikhil.trello.model.ProjectRole;
import com.nikhil.trello.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;

    public ProjectMember createProject(Project project, User user, ProjectRole role){
        ProjectMember member = new ProjectMember(project, user, role);
        return projectMemberRepository.save(member);
    }



}
