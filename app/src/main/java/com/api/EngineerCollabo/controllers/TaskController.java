package com.api.EngineerCollabo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityNotFoundException;

import com.api.EngineerCollabo.entities.Task;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseProjectTasks;
import com.api.EngineerCollabo.entities.ResponseTask;
import com.api.EngineerCollabo.entities.ResponseTasks;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.TaskRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ProjectService;
import com.api.EngineerCollabo.services.TaskService;
import com.api.EngineerCollabo.services.UserService;

@RestController

// @RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    // @Autowired
    // ResponseTasks responseTasks;

    @PostMapping("/tasks/create")
    public void createTask(@RequestBody Task requestTask) {
        String name = requestTask.getName();
        Date doneAt = requestTask.getDoneAt();
        Integer projectId = requestTask.getProjectId();
        Date deadline = requestTask.getDeadline();
        String description = requestTask.getDescription();
        Integer inChargeUserId = requestTask.getInChargeUserId();

        if (inChargeUserId != null && projectId != null) {
            taskService.createTask(name, doneAt, projectId, deadline, description, inChargeUserId);
        }
    }

    // TODO ユーザーが所属していないプロジェクト･グループのタスクを取得できてしまうので､修正が必要
    @GetMapping("/tasks")
    public Optional<ResponseTasks> responseTask(
        @RequestParam(name = "projectId", required = false) Integer projectId,
        @RequestParam(name = "userId", required = true) Integer userId,
        @RequestParam(name = "milestoneId", required = false) Integer milestoneId) {

        List<Task> tasks = null;

        if (userId != null) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                tasks = user.get().getTasks();
            }
        }

        if (projectId != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getProjectId().equals(projectId))
                    .collect(Collectors.toList());
        }

        if (milestoneId != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getMilestoneId().equals(milestoneId))
                    .collect(Collectors.toList());
        }

        ResponseTasks responseTasks = new ResponseTasks();
        responseTasks.setUserId(userId);
        responseTasks.setProjectId(projectId);
        responseTasks.setTasks(tasks);
        return Optional.of(responseTasks);
    }

    @GetMapping("/tasks/{id}")
    public ResponseTask responseTask(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
            return taskService.changeResponseTask(task);
        } else {
            return null;
        }
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable("id") Integer id, @RequestBody Task requestTask) {
        try {
            Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("タスクID: " + id + " が見つかりません"));

            String name = requestTask.getName();
            if (name != null) {
                task.setName(name);
            }

            String description = requestTask.getDescription();
            if (description != null) {
                task.setDescription(description);
            }

            Date idDone = requestTask.getDoneAt();
            if (idDone != null) {
                task.setDoneAt(idDone);
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

            Task savedTask = taskRepository.save(task);
            return ResponseEntity.ok(savedTask);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            taskRepository.deleteById(id);
        }
    }

    @GetMapping("/projects/{projectId}/users/{userId}/tasks")
    public ResponseTasks getTasksByProjectAndUser(@PathVariable Integer projectId, @PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        List<Task>tasks = user.get().getTasks().stream()
            .filter(task -> task.getProjectId() == projectId)
            .toList();
        ResponseTasks responseTasks = new ResponseTasks();
        responseTasks.setUserId(userId);
        responseTasks.setProjectId(projectId);
        responseTasks.setTasks(tasks);
        return responseTasks;
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseProjectTasks getTasksByProject(@PathVariable Integer projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        List<Task>tasks = project.get().getTasks();
        ResponseProjectTasks responseProjectTasks = new ResponseProjectTasks();
        responseProjectTasks.setProjectId(projectId);
        responseProjectTasks.setTasks(tasks);
        return responseProjectTasks;
    }
}
