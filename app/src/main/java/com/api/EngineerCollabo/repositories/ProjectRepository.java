package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.File;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ProjectNotice;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    
    Project findById(int id);

    void deleteById(int id);

    Project findByUsers(User user);

    Project findByProjectNotices(ProjectNotice projectNotice);

    Project findByDirectories(Directory directory);

    Project findByFiles(File file);

    List<Project> findByNameLike(String searchWord);

    List<Project> findByDescriptionLike(String searchWord);
}