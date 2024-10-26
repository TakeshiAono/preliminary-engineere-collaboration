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

    // TODO: そのファイルをダウンロードできるか資格があるかどうか認可が必ず必要
    // TODO: 必要最低限のポリシーでIAMを設定する
    @GetMapping("/{id}/download-signature-url")
    public String responseDownloadSignatureUrl(
        @PathVariable("id")
        String name,
        @RequestParam
        String directoryName
    ) {
        Map<String, String> metadata = new HashMap<>();
        String joinFileName;
        if(directoryName.equals("undefined")) {
            System.out.println("a");
            joinFileName = name;
        } else {
            System.out.println("b");
            joinFileName = directoryName + "/" + name;
        }
        return this.createPresignedGetUrl("test9898989", joinFileName);
    }

    private String createPresignedGetUrl(String bucketName, String keyName) {
        try (S3Presigner presigner = S3Presigner.builder().region(Region.US_EAST_1).build()) {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                // .contentType(metadata.get("Content-Type"))
                .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(1))  // The URL will expire in 10 minutes.
                .getObjectRequest(objectRequest)
                .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            System.out.println("Presigned URL:");
            System.out.println(presignedRequest.url());
            return presignedRequest.url().toExternalForm();
        }
    }

    // TODO: そのファイルをダウンロードできるか資格があるかどうか認可が必ず必要
    // TODO: 必要最低限のポリシーでIAMを設定する
    @GetMapping("/{fileName}/upload-signature-url")
    public String responseUploadSignatureUrl(
        @PathVariable("fileName")
        String fileName,
        @RequestParam
        String fileType,
        @RequestParam
        String directoryName
    ) {
        Map<String, String> metadata = new HashMap<>();
        System.out.println("コンテントたいぷ");
        System.out.println(fileType);
        System.out.println(directoryName);
        metadata.put("Content-Type", fileType);

        String joinFileName;
        if(directoryName.equals("undefined")) {
            joinFileName = fileName;
        } else {
            joinFileName = directoryName + "/" + fileName;
        }
        // metadata.put("x-amz-meta-author", "John Doe");
        System.out.println(2);
        System.out.println(joinFileName);
        return this.createPresignedUrl("test9898989", joinFileName, metadata);
    }

    public String createPresignedUrl(String bucketName, String keyName, Map<String, String> metadata) {
        try (S3Presigner presigner = S3Presigner.create()) {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .contentType(metadata.get("Content-Type"))
                    // .metadata(metadata)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();

            return presignedRequest.url().toExternalForm();
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
