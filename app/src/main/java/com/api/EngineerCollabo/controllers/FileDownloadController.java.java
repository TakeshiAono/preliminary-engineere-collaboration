package com.api.EngineerCollabo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileDownloadController {

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile() throws IOException {
        System.out.println("テスト1");
        Resource resource = new ClassPathResource("test.txt");
        System.out.println("テスト2");
        InputStream inputStream = resource.getInputStream();
        System.out.println("テスト3");
        byte[] fileContent = inputStream.readAllBytes();
        System.out.println("テスト");
        System.out.println(fileContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "test.txt");

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}