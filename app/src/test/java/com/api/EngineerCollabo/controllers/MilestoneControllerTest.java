package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Milestone;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.entities.ResponseMilestone;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MilestoneControllerTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private MilestoneController milestoneController;

    @Test
    void getMilestonesByProject() {
        // Arrange
        Integer projectId = 1;
        Project project = new Project();
        Milestone milestone1 = new Milestone();
        milestone1.setId(1);
        milestone1.setName("Milestone 1");
        milestone1.setDeadline(new Date());
        Milestone milestone2 = new Milestone();
        milestone2.setId(2);
        milestone2.setName("Milestone 2");
        milestone2.setDeadline(new Date());

        project.setMilestones(Arrays.asList(milestone1, milestone2));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act
        List<ResponseMilestone> responseMilestones = milestoneController.getMilestonesByProject(projectId);

        // Assert
        assertEquals(2, responseMilestones.size());
        assertEquals("Milestone 1", responseMilestones.get(0).getName());
        assertEquals("Milestone 2", responseMilestones.get(1).getName());
    }
}
