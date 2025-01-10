package com.api.EngineerCollabo.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

// TODO テスト作成
@RestController
public class FileDownloadController {

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile() throws IOException {
        // resourcesディレクトリからファイルを読み込む
        Resource resource = new ClassPathResource("test.txt");
        InputStream inputStream = resource.getInputStream();
        byte[] data = inputStream.readAllBytes();

        // ファイルダウンロード時のヘッダーを設定
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "test.txt");
        headers.setContentLength(data.length);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
