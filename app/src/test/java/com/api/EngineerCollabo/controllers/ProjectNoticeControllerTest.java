package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.ProjectNotice;
import com.api.EngineerCollabo.entities.ResponseProjectNotice;
import com.api.EngineerCollabo.repositories.ProjectNoticeRepository;
import com.api.EngineerCollabo.services.ProjectNoticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProjectNoticeControllerTest {

    @Mock
    private ProjectNoticeRepository projectNoticeRepository;

    @Mock
    private ProjectNoticeService projectNoticeService;

    @InjectMocks
    private ProjectNoticeController projectNoticeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProjectNotice() {
        ProjectNotice projectNotice = new ProjectNotice();
        projectNotice.setLog("Test log");
        projectNotice.setProjectId(1);

        projectNoticeController.createProjectNotice(projectNotice);

        verify(projectNoticeService, times(1)).createProjectNotice("Test log", 1);
    }

    @Test
    void testResponseProjectNotice() {
        int projectNoticeId = 1;
        ProjectNotice projectNotice = new ProjectNotice();
        projectNotice.setId(projectNoticeId);

        when(projectNoticeRepository.findById(projectNoticeId)).thenReturn(projectNotice);
        when(projectNoticeService.changResponseProjectNotice(any(ProjectNotice.class))).thenReturn(new ResponseProjectNotice());

        ResponseProjectNotice response = projectNoticeController.responseProjectNotice(Optional.of(projectNoticeId));

        assertEquals(new ResponseProjectNotice(), response);
    }

    @Test
    void testPutProjectNotice() {
        int projectNoticeId = 1;
        ProjectNotice existingProjectNotice = new ProjectNotice();
        existingProjectNotice.setId(projectNoticeId);
        existingProjectNotice.setLog("Old log");

        ProjectNotice updatedProjectNotice = new ProjectNotice();
        updatedProjectNotice.setLog("Updated log");

        when(projectNoticeRepository.findById(projectNoticeId)).thenReturn(existingProjectNotice);

        projectNoticeController.putProjectNotice(Optional.of(projectNoticeId), updatedProjectNotice);

        verify(projectNoticeRepository, times(1)).save(existingProjectNotice);
        assertEquals("Updated log", existingProjectNotice.getLog());
    }

    @Test
    void testDeleteProjectNotice() {
        int projectNoticeId = 1;

        projectNoticeController.deleteProjectNotice(Optional.of(projectNoticeId));

        verify(projectNoticeRepository, times(1)).deleteById(projectNoticeId);
    }
}
