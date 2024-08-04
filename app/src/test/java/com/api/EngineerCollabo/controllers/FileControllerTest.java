package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.File;
import com.api.EngineerCollabo.entities.ResponseFile;
import com.api.EngineerCollabo.repositories.FileRepository;
import com.api.EngineerCollabo.services.FileService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @Test
    void createFile() {
        // 準備
        File requestFile = new File();
        requestFile.setName("Test File");
        requestFile.setFileUrl("http://example.com/file");
        requestFile.setDirectoryId(1);

        // 実行
        fileController.createFile(requestFile);

        // 検証
        verify(fileService, times(1)).createFile("Test File", "http://example.com/file", 1);
    }

    @Test
    void responseFile() {
        // 準備
        int fileId = 1;
        File file = new File();
        file.setId(fileId);

        ResponseFile expectedResponse = new ResponseFile();
        expectedResponse.setId(fileId);

        // モックの設定
        when(fileRepository.findById(fileId)).thenReturn(file);
        when(fileService.changeResponseFile(file)).thenReturn(expectedResponse);

        // 実行
        ResponseFile actualResponse = fileController.responseFile(Optional.of(fileId));

        // 検証
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void putFile() {
        // 準備
        int fileId = 1;
        File file = new File();
        file.setId(fileId);
        file.setName("Original Name");

        File requestFile = new File();
        requestFile.setName("Updated Name");
        requestFile.setFileUrl("http://example.com/new-file");

        // モックの設定
        when(fileRepository.findById(fileId)).thenReturn(file);

        // 実行
        fileController.putFile(Optional.of(fileId), requestFile);

        // 検証
        verify(fileRepository, times(1)).save(file);
        assertEquals("Updated Name", file.getName());
        assertEquals("http://example.com/new-file", file.getFileUrl());
    }

    @Test
    void deleteFile() {
        // 準備
        int fileId = 1;

        // モックの設定
        doNothing().when(fileRepository).deleteById(fileId);

        // 実行
        fileController.deleteFile(Optional.of(fileId));

        // 検証
        verify(fileRepository, times(1)).deleteById(fileId);
    }
}
