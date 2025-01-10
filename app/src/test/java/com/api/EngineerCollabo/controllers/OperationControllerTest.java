package com.api.EngineerCollabo.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.EngineerCollabo.entities.Operation;
import com.api.EngineerCollabo.entities.ResponseOperation;
import com.api.EngineerCollabo.repositories.OperationRepository;
import com.api.EngineerCollabo.services.OperationService;

import java.util.Optional;

public class OperationControllerTest {

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private OperationService operationService;

    @InjectMocks
    private OperationController operationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMessage_Success() {
        // 準備
        Operation operation = new Operation();
        operation.setLog("Test log");
        operation.setProjectId(1);

        // 実行
        operationController.createMessage(operation);

        // 検証
        verify(operationService, times(1)).createOperation("Test log", 1);
    }

    @Test
    void responseOperation_Success() {
        // 準備
        int operationId = 1;
        Operation operation = new Operation();
        operation.setId(operationId);
        ResponseOperation responseOperation = new ResponseOperation();
        responseOperation.setId(operationId);

        when(operationRepository.findById(operationId)).thenReturn(operation);
        when(operationService.changeResponseOperation(operation)).thenReturn(responseOperation);

        // 実行
        ResponseOperation actualResponse = operationController.responseOperation(Optional.of(operationId));

        // 検証
        assertEquals(responseOperation, actualResponse);
    }

    @Test
    void putOperation_Success() {
        // 準備
        int operationId = 1;
        Operation existingOperation = new Operation();
        existingOperation.setId(operationId);
        existingOperation.setLog("Old log");

        Operation updatedOperation = new Operation();
        updatedOperation.setLog("New log");

        when(operationRepository.findById(operationId)).thenReturn(existingOperation);

        // 実行
        operationController.putOperation(Optional.of(operationId), updatedOperation);

        // 検証
        verify(operationRepository, times(1)).save(existingOperation);
        assertEquals("New log", existingOperation.getLog());
    }

    @Test
    void deleteOperation_Success() {
        // 準備
        int operationId = 1;

        // 実行
        operationController.deleteOperation(Optional.of(operationId));

        // 検証
        verify(operationRepository, times(1)).deleteById(operationId);
    }
}
