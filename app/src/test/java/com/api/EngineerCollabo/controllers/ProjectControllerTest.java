package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseProject;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void responseProject() {
        // 準備
        int projectId = 1;
        Project project = new Project();
        project.setId(projectId);
        ResponseProject expectedResponse = new ResponseProject();
        expectedResponse.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(project);
        when(projectService.changeResponseProject(project)).thenReturn(expectedResponse);

        // 実行
        ResponseProject actualResponse = projectController.responseProject(Optional.of(projectId));

        // 検証
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void responseProjects() {
        // 準備
        List<Project> projects = Arrays.asList(new Project(), new Project());
        List<ResponseProject> expectedResponses = Arrays.asList(new ResponseProject(), new ResponseProject());

        when(projectRepository.findAll()).thenReturn(projects);
        when(projectService.changeResponseProject(any(Project.class))).thenReturn(expectedResponses.get(0), expectedResponses.get(1));

        // 実行
        List<ResponseProject> actualResponses = projectController.responseProjects();

        // 検証
        assertEquals(expectedResponses.size(), actualResponses.size());
    }

    @Test
    void putProject() {
        // 準備
        int projectId = 1;
        Project project = new Project();
        Project requestProject = new Project();
        requestProject.setName("Updated Project");

        when(projectRepository.findById(projectId)).thenReturn(project);

        // 実行
        projectController.putProject(Optional.of(projectId), requestProject);

        // 検証
        verify(projectRepository).save(project);
        assertEquals("Updated Project", project.getName());
    }

    @Test
    void deleteProject() {
        // 準備
        int projectId = 1;

        // 実行
        projectController.deleteProject(Optional.of(projectId));

        // 検証
        verify(projectRepository).deleteById(projectId);
    }

    @Test
    void createProject() {
        // 準備
        Project requestProject = new Project();

        // 実行
        projectController.createProject(requestProject);

        // 検証
        verify(projectRepository).save(requestProject);
    }
    @Test
    void searchProject() {
        // TODO
    }
}
