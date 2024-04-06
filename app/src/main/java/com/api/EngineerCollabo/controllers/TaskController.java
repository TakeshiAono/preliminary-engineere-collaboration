package com.api.EngineerCollabo.controllers;

import java.util.Date;
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

import com.api.EngineerCollabo.entities.Task;
import com.api.EngineerCollabo.entities.ResponseTask;
import com.api.EngineerCollabo.repositories.TaskRepository;
import com.api.EngineerCollabo.services.TaskService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public void createTask(@RequestBody Task requestTask) {
        String name = requestTask.getName();
        Boolean isDone = requestTask.getIsDone();
        Integer projectId = requestTask.getProjectId();
        Date deadline = requestTask.getDeadline();
        String description = requestTask.getDescription();
        Integer inChargeUserId = requestTask.getInChargeUserId();

        if (inChargeUserId != null && projectId != null) {
            taskService.createTask(name, isDone, projectId, deadline, description, inChargeUserId);
        }
    }

    @GetMapping("/{id}")
    public ResponseTask responseTask(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Task task = taskRepository.findById(id);
            return taskService.changeResponseTask(task);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putTask(@PathVariable("id") Optional<Integer> ID, @RequestBody Task requestTask) {
        if (ID.isPresent()) {
            int id = ID.get();
            Task task = taskRepository.findById(id);

            String name = requestTask.getName();
            if (name != null) {
                task.setName(name);
            }

            String description = requestTask.getDescription();
            if (description != null) {
                task.setDescription(description);
            }

            Boolean idDone = requestTask.getIsDone();
            if (idDone != null) {
                task.setIsDone(idDone);
            }

            Date deadline = requestTask.getDeadline();
            if (deadline != null) {
                task.setDeadline(deadline);
            }

            Integer projectId = requestTask.getProjectId();
            if (projectId != null) {
                task.setProjectId(projectId);
            }

            Integer userId = requestTask.getInChargeUserId();
            if (userId != null) {
                task.setInChargeUserId(userId);
            }

            taskRepository.save(task);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            taskRepository.deleteById(id);
        }
    }
}
