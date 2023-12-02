package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DirectoryRepository extends JpaRepository<Directory, Integer> {

    Directory findById(int id);

    Directory findByName(String name);

    List<Directory> findByProject(Project project);

    Directory findByFiles(File file);
}