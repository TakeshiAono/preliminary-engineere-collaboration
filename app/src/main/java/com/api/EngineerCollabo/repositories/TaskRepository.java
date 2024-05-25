package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findById(int id);

    // List<Task> findByUser(User user);

    List<Task> findByProject(Project project);
}