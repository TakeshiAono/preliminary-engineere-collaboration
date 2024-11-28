package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Application;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.Project;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    
    Application findById(int id);

    Application findByMessage(String message);

    void deleteById(int id);

    List<Application> findByUser(User user);

    List<Application> findByProject(Project project);
}