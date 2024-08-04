package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.ResponseDirectory;
import com.api.EngineerCollabo.repositories.DirectoryRepository;
import com.api.EngineerCollabo.services.DirectoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DirectoryControllerTest {

    @Mock
    private DirectoryRepository directoryRepository;

    @Mock
    private DirectoryService directoryService;

    @InjectMocks
    private DirectoryController directoryController;

    @Test
    void createDirectory() {
        // 準備
        Directory requestDirectory = new Directory();
        requestDirectory.setName("Test Directory");
        requestDirectory.setProjectId(1);

        // 実行
        directoryController.createDirectory(requestDirectory);

        // 検証
        verify(directoryService, times(1)).createDirectory("Test Directory", 1);
    }

    @Test
    void responseDirectory() {
        // 準備
        int directoryId = 1;
        Directory directory = new Directory();
        directory.setId(directoryId);

        ResponseDirectory expectedResponse = new ResponseDirectory();
        expectedResponse.setId(directoryId);

        // モックの設定
        when(directoryRepository.findById(directoryId)).thenReturn(directory);
        when(directoryService.changeResponseDirectory(directory)).thenReturn(expectedResponse);

        // 実行
        ResponseDirectory actualResponse = directoryController.responseDirectory(Optional.of(directoryId));

        // 検証
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void putDirectory() {
        // 準備
        int directoryId = 1;
        Directory directory = new Directory();
        directory.setId(directoryId);
        directory.setName("Original Name");

        Directory requestDirectory = new Directory();
        requestDirectory.setName("Updated Name");

        // モックの設定
        when(directoryRepository.findById(directoryId)).thenReturn(directory);

        // 実行
        directoryController.putDirectory(Optional.of(directoryId), requestDirectory);

        // 検証
        verify(directoryRepository, times(1)).save(directory);
        assertEquals("Updated Name", directory.getName());
    }

    @Test
    void deleteDirectory() {
        // 準備
        int directoryId = 1;

        // モックの設定
        doNothing().when(directoryRepository).deleteById(directoryId);

        // 実行
        directoryController.deleteDirectory(Optional.of(directoryId));

        // 検証
        verify(directoryRepository, times(1)).deleteById(directoryId);
    }
}
