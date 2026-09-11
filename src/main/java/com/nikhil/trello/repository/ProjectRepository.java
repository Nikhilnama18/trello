package com.nikhil.trello.repository;

import com.nikhil.trello.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Page<Project> findByCreatedById(UUID userId, Pageable pageable);
    Optional<Project> findByIdAndCreatedById(UUID id, UUID userId);
}
