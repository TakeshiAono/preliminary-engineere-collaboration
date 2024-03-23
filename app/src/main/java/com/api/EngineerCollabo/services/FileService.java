package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.File;
import com.api.EngineerCollabo.entities.ResponseFile;
import com.api.EngineerCollabo.repositories.DirectoryRepository;
import com.api.EngineerCollabo.repositories.FileRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileService {

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private FileRepository fileRepository;

    public File createFile(String name, String fileUrl, Integer directoryId) {
        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new EntityNotFoundException("Directory not found"));
        File file = new File();
        file.setName(name);
        file.setFileUrl(fileUrl);
        file.setDirectoryId(directory.getId());
        return fileRepository.save(file);
    }

    public ResponseFile changeResponseFile(File file) {
        ResponseFile responseFile = new ResponseFile();
        responseFile.setId(file.getId());
        responseFile.setName(file.getName());
        responseFile.setFileUrl(file.getFileUrl());
        responseFile.setDirectoryId(file.getDirectory().getId());
        return responseFile;
    }

}