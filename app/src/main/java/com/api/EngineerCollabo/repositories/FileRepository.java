package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {

    File findById(int id);
    
    File findByName(String name);

    List<File> findByProject(Project project);

    List<File> findByDirectory(Directory directory);
}