package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findById(int id);

    // Project findByUsers(User user);

    Project findByProjectNotices(ProjectNotice projectNotice);

    Project findByDirectories(Directory directory);

    Project findByFiles(File file);
}