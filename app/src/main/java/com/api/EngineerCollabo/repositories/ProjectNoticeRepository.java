package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ProjectNotice;

public interface ProjectNoticeRepository extends JpaRepository<ProjectNotice, Integer> {

    ProjectNotice findById(int id);

    ProjectNotice findByProject(Project project);
}