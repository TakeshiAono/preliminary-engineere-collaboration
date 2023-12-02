package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectNoticeRepository extends JpaRepository<ProjectNotice, Integer> {

    ProjectNotice findById(int id);

    ProjectNotice findByProject(Project project);
}