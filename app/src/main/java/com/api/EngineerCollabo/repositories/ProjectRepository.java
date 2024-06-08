package com.api.EngineerCollabo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ProjectNotice;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findById(int id);

    // Project findByUsers(User user);

    void deleteById(int id);

    Project findByProjectNotices(ProjectNotice projectNotice);

    Project findByDirectories(Directory directory);

    // Project findByFiles(File file);

    List<Project> findByNameLike(String searchWord);

    List<Project> findByDescriptionLike(String searchWord);

    // List<Project> findByDeadlineAtAfter(Date deadline);
    List<Project> findByDeadlineAfter(Date date);

    List<Project> findByDeadlineBetween(Date startDate, Date endDate);

    @Query(value = "SELECT p.* FROM projects p " +
                   "LEFT JOIN projects_users pu ON p.id = pu.project_id " +
                   "LEFT JOIN users u ON pu.users_id = u.id " +
                   "WHERE (p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.recruiting_text LIKE %:keyword%) " +
                   "AND p.deadline BETWEEN :startDate AND :endDate AND CAST(p.use_technology AS TEXT) LIKE %:selectedSkills% " +
                   "GROUP BY p.id " +
                   "HAVING COUNT(u.id) >= :userCount", nativeQuery = true)
    List<Project> findProjectSomeParams(
        @Param("keyword") String keyword,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        @Param("userCount") Integer userCount,
        @Param("selectedSkills") String selectedSkills
    );
}