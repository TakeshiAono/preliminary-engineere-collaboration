package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.ResponseOperation;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.Operation;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.OperationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Operation createOperation(String log, Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        Operation operation = new Operation();
        operation.setLog(log);
        operation.setProjectId(project.getId());

        return operationRepository.save(operation);
    }

    public ResponseOperation changeResponseOperation(Operation operation) {
        ResponseOperation responseOperation = new ResponseOperation();
        responseOperation.setId(operation.getId());
        responseOperation.setLog(operation.getLog());
        responseOperation.setProjectId(operation.getProjectId());
        responseOperation.setCreatedAt(operation.getCreatedAt());
        responseOperation.setUpdatedAt(operation.getUpdatedAt());
        return responseOperation;
    }
}