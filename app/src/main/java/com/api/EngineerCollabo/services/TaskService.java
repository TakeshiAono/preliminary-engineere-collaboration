package com.api.EngineerCollabo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.Task;
import com.api.EngineerCollabo.entities.ResponseTask;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.TaskRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String name, Boolean isDone, Integer projectId, Date deadline, String description, Integer inChargeUserId) {
        User inChargeUser = userRepository.findById(inChargeUserId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        Task task = new Task();
        task.setName(name);
        task.setIsDone(isDone);
        task.setDeadline(deadline);
        task.setDescription(description);
        task.setProjectId(project.getId());
        task.setInChargeUserId(inChargeUser.getId());

        return taskRepository.save(task);
    }

    public ResponseTask changeResponseTask(Task task) {
        ResponseTask responseTask = new ResponseTask();
        responseTask.setId(task.getId());
        responseTask.setName(task.getName());
        responseTask.setIsDone(task.getIsDone());
        responseTask.setDeadline(task.getDeadline());
        responseTask.setDescription(task.getDescription());
        responseTask.setProjectId(task.getProject().getId());
        responseTask.setInChargeUserId(task.getUser().getId());
        responseTask.setCreatedAt(task.getCreatedAt());
        responseTask.setUpdatedAt(task.getUpdatedAt());
        return responseTask;
    }
}