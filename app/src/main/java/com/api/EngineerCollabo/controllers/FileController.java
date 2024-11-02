package com.api.EngineerCollabo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.EngineerCollabo.entities.File;
import com.api.EngineerCollabo.entities.ResponseFile;
import com.api.EngineerCollabo.repositories.FileRepository;
import com.api.EngineerCollabo.services.FileService;

import org.slf4j.Logger;
import software.amazon.awssdk.http.HttpExecuteRequest;
import software.amazon.awssdk.http.HttpExecuteResponse;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.http.SdkHttpRequest;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.utils.IoUtils;
import software.amazon.awssdk.regions.Region;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "http://localhost:5173")
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
