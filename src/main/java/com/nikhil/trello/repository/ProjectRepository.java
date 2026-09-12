package com.nikhil.trello.repository;

import com.nikhil.trello.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Page<Project> findByCreatedById(UUID userId, Pageable pageable);
    Optional<Project> findProjectById(UUID id);
    Optional<Project> findProjectByIdAndCreatedById(UUID id, UUID userId);
    boolean existsByProjectIdAndUserId(UUID id, UUID userId);
}
