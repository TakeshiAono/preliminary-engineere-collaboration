package com.api.EngineerCollabo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.Member;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ProjectNotice;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findById(int id);

    // Project findByUsers(User user);

    void deleteById(int id);

    Project findByMembers(Member member);

    Project findByProjectNotices(ProjectNotice projectNotice);

    Project findByDirectories(Directory directory);

    // Project findByFiles(File file);

    List<Project> findByNameLike(String searchWord);

    List<Project> findByDescriptionLike(String searchWord);
}