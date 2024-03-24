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

import com.api.EngineerCollabo.entities.ProjectNotice;
import com.api.EngineerCollabo.entities.ResponseProjectNotice;
import com.api.EngineerCollabo.repositories.ProjectNoticeRepository;
import com.api.EngineerCollabo.services.ProjectNoticeService;

@RestController
@RequestMapping("/projectNotices")
public class ProjectNoticeController {
    @Autowired
    ProjectNoticeRepository projectNoticeRepository;

    @Autowired
    ProjectNoticeService projectNoticeService;

    @PostMapping("/create")
    public void createProjectNotice(@RequestBody ProjectNotice requestProjectNotice) {
        String log = requestProjectNotice.getLog();
        Integer projectId = requestProjectNotice.getProjectId();

        if (projectId != null) {
            projectNoticeService.createProjectNotice(log, projectId);
        }
    }

    @GetMapping("{id}")
    public ResponseProjectNotice responseProjectNotice(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            ProjectNotice projectNotice = projectNoticeRepository.findById(id);
            return projectNoticeService.changResponseProjectNotice(projectNotice);
        } else {
            return null;
        }
    }

    @PatchMapping("{id}")
    public void putProjectNotice(@PathVariable("id") Optional<Integer> ID,
            @RequestBody ProjectNotice requestProjectNotice) {
        if (ID.isPresent()) {
            int id = ID.get();
            ProjectNotice projectNotice = projectNoticeRepository.findById(id);

            String log = requestProjectNotice.getLog();
            if (log != null) {
                projectNotice.setLog(log);
            }
            projectNoticeRepository.save(projectNotice);
        }
    }

    @DeleteMapping("{id}")
    public void deleteProjectNotice(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            projectNoticeRepository.deleteById(id);
        }
    }
}
