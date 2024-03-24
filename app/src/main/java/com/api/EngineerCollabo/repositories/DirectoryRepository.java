package com.api.EngineerCollabo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.File;
import com.api.EngineerCollabo.entities.Project;

public interface DirectoryRepository extends JpaRepository<Directory, Integer> {

    Directory findById(int id);

    Directory findByName(String name);

    List<Directory> findByProject(Project project);

    Directory findByFiles(File file);
}