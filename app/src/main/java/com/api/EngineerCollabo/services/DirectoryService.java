package com.api.EngineerCollabo.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseDirectory;
import com.api.EngineerCollabo.repositories.DirectoryRepository;
import com.api.EngineerCollabo.repositories.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DirectoryService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    public Directory createDirectory(String name, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        Directory directory = new Directory();
        directory.setName(name);
        directory.setProjectId(project.getId());

        return directoryRepository.save(directory);
    }

    public ResponseDirectory changeResponseDirectory(Directory directory) {
        ResponseDirectory responseDirectory = new ResponseDirectory();
        responseDirectory.setId(directory.getId());
        responseDirectory.setName(directory.getName());
        responseDirectory.setProjectId(directory.getProject().getId());
        responseDirectory.setFileIds(
                directory.getFiles().stream().map(file -> file.getId()).collect(Collectors.toList()));

        return responseDirectory;
    }
}