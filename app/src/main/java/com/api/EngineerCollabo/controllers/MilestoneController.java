package com.api.EngineerCollabo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseMilestone;
import com.api.EngineerCollabo.entities.Milestone;

import com.api.EngineerCollabo.repositories.ProjectRepository;

@RestController
public class MilestoneController {
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/projects/{projectId}/milestones")
    public List<ResponseMilestone> getMilestonesByProject(@PathVariable Integer projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        List<Milestone> milestones = project.get().getMilestones();
        List<ResponseMilestone> responseMilestones = milestones.stream().map(milestone -> {
            ResponseMilestone responseMilestone = new ResponseMilestone();
            responseMilestone.setId(milestone.getId());
            responseMilestone.setName(milestone.getName());
            responseMilestone.setDeadline(milestone.getDeadline());
            return responseMilestone;
        }).collect(Collectors.toList());
        return responseMilestones;
    }
}
