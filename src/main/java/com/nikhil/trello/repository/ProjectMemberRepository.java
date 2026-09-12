package com.nikhil.trello.repository;

import com.nikhil.trello.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {

    Optional<ProjectMember>getMemberByProjectAndUserId(UUID projectId, UUID userId);
}
