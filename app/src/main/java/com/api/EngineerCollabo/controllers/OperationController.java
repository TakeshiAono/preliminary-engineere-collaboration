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
import org.springframework.web.bind.annotation.CrossOrigin;

import com.api.EngineerCollabo.entities.Operation;
import com.api.EngineerCollabo.entities.ResponseOperation;
import com.api.EngineerCollabo.repositories.OperationRepository;
// import com.api.EngineerCollabo.services.MessageService;
import com.api.EngineerCollabo.services.OperationService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/operations")
public class OperationController {
    @Autowired
    OperationRepository operationRepository;

    @Autowired
    OperationService operationService;

    @PostMapping("/create")
    public void createMessage(@RequestBody Operation requestOperation) {
        String log = requestOperation.getLog();
        Integer projectId = requestOperation.getProjectId();

        if (projectId != null) {
            operationService.createOperation(log, projectId);
        }
    }

    @GetMapping("/{id}")
    public ResponseOperation responseOperation(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Operation operation = operationRepository.findById(id);
            return operationService.changeResponseOperation(operation);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putOperation(@PathVariable("id") Optional<Integer> ID, @RequestBody Operation requestOperation) {
        if (ID.isPresent()) {
            int id = ID.get();
            Operation operation = operationRepository.findById(id);

            String log = requestOperation.getLog();
            if (log != null) {
                operation.setLog(log);
            }
            operationRepository.save(operation);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOperation(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            operationRepository.deleteById(id);
        }
    }

}
