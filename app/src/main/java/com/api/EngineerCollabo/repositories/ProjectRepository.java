package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
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