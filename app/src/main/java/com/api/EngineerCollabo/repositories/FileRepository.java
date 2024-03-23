package com.api.EngineerCollabo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.File;

public interface FileRepository extends JpaRepository<File, Integer> {

    File findById(int id);

    File findByName(String name);

    // List<File> findByProject(Project project);

    List<File> findByDirectory(Directory directory);
}