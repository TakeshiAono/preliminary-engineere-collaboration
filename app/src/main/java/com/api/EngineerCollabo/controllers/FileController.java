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

import com.api.EngineerCollabo.entities.File;
import com.api.EngineerCollabo.entities.ResponseFile;
import com.api.EngineerCollabo.repositories.FileRepository;
import com.api.EngineerCollabo.services.FileService;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileService fileService;

    @PostMapping("/create")
    public void createFile(@RequestBody File requestFile) {
        String name = requestFile.getName();
        String fileUrl = requestFile.getFileUrl();
        Integer directoryId = requestFile.getDirectoryId();

        if (directoryId != null) {
            fileService.createFile(name, fileUrl, directoryId);
        }
    }

    @GetMapping("{id}")
    public ResponseFile responseFile(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            File file = fileRepository.findById(id);
            return fileService.changeResponseFile(file);
        } else {
            return null;
        }
    }

    @PatchMapping("{id}")
    public void putFile(@PathVariable("id") Optional<Integer> ID, @RequestBody File requestFile) {
        if (ID.isPresent()) {
            int id = ID.get();
            File file = fileRepository.findById(id);

            String name = requestFile.getName();
            if (name != null) {
                file.setName(name);
            }

            String fileUrl = requestFile.getFileUrl();
            if (fileUrl != null) {
                file.setFileUrl(fileUrl);
            }

            fileRepository.save(file);
        }
    }

    @DeleteMapping("{id}")
    public void deleteFile(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            fileRepository.deleteById(id);
        }
    }
}
