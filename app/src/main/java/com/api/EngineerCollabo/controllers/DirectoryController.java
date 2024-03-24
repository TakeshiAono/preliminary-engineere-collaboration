package com.api.EngineerCollabo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.ResponseDirectory;
import com.api.EngineerCollabo.repositories.DirectoryRepository;
import com.api.EngineerCollabo.services.DirectoryService;

@RestController
@RequestMapping("/directories")
public class DirectoryController {
    @Autowired
    DirectoryRepository directoryRepository;

    @Autowired
    DirectoryService directoryService;

    @PostMapping("/create")
    public void createDirectory(@RequestBody Directory requestDirectory) {
        String name = requestDirectory.getName();
        Integer projectId = requestDirectory.getProjectId();

        if (projectId != null) {
            directoryService.createDirectory(name, projectId);
        }
    }

    @GetMapping("{id}")
    public ResponseDirectory responseDirectory(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Directory directory = directoryRepository.findById(id);
            return directoryService.changeResponseDirectory(directory);
        } else {
            return null;
        }
    }

    @PatchMapping("{id}")
    public void putDirectory(@PathVariable("id") Optional<Integer> ID, @RequestBody Directory requestDirectory) {
        if (ID.isPresent()) {
            int id = ID.get();
            Directory directory = directoryRepository.findById(id);

            String name = requestDirectory.getName();
            if (name != null) {
                directory.setName(name);
            }
            directoryRepository.save(directory);
        }
    }

    @DeleteMapping("{id}")
    public void deleteDirectory(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            directoryRepository.deleteById(id);
        }
    }
}
