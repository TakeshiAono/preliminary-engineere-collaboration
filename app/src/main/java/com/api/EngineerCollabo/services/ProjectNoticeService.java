package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ProjectNotice;
import com.api.EngineerCollabo.entities.ResponseProjectNotice;
import com.api.EngineerCollabo.repositories.ProjectNoticeRepository;
import com.api.EngineerCollabo.repositories.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectNoticeService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectNoticeRepository projectNoticeRepository;

    public ProjectNotice createProjectNotice(String log, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        ProjectNotice projectNotice = new ProjectNotice();
        projectNotice.setLog(log);
        projectNotice.setProjectId(project.getId());

        return projectNoticeRepository.save(projectNotice);
    }

    public ResponseProjectNotice changResponseProjectNotice(ProjectNotice projectNotice) {
        ResponseProjectNotice responseProjectNotice = new ResponseProjectNotice();
        responseProjectNotice.setId(projectNotice.getId());
        responseProjectNotice.setLog(projectNotice.getLog());
        responseProjectNotice.setProjectId(projectNotice.getProject().getId());
        return responseProjectNotice;
    }
}