package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Task;
import com.api.EngineerCollabo.entities.ResponseTask;
import com.api.EngineerCollabo.entities.ResponseTasks;
import com.api.EngineerCollabo.repositories.TaskRepository;
import com.api.EngineerCollabo.services.TaskService;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskService taskService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        // モックの設定
        String name = "New Task";
        Date doneAt = new Date();
        Integer projectId = 1;
        Date deadline = new Date();
        String description = "Task description";
        Integer inChargeUserId = 1;

        Task requestTask = new Task();
        requestTask.setName(name);
        requestTask.setDoneAt(doneAt);
        requestTask.setProjectId(projectId);
        requestTask.setDeadline(deadline);
        requestTask.setDescription(description);
        requestTask.setInChargeUserId(inChargeUserId);

        // 実行
        taskController.createTask(requestTask);

        // 検証
        verify(taskService, times(1)).createTask(name, doneAt, projectId, deadline, description, inChargeUserId);
    }

    @Test
    void testResponseTask() {
        // モックの設定
        int taskId = 1;
        Task task = new Task();
        task.setId(taskId);
        task.setName("Test Task");

        ResponseTask expectedResponse = new ResponseTask();
        // ... expectedResponseの設定 ...

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskService.changeResponseTask(task)).thenReturn(expectedResponse);

        // 実行
        ResponseEntity<ResponseTask> response = taskController.responseTask(Optional.of(taskId));
        
        // 検証
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testPatchTask() {
        // モックの設定
        int taskId = 1;
        Task task = new Task();
        task.setId(taskId);
        task.setName("Test Task");

        Task requestTask = new Task();
        requestTask.setName("Updated Task");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // 実行
        ResponseEntity<Task> responseEntity = taskController.patchTask(taskId, requestTask);


        // 検証
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteTask() {
        // モックの設定
        int taskId = 1;

        // 実行
        taskController.deleteTask(Optional.of(taskId));

        // 検証
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
