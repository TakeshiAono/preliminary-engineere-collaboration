package com.api.EngineerCollabo.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseProject;

@Service
public class ProjectService {

  public ResponseProject changeResponseProject(Project project) {
    ResponseProject responseProject = new ResponseProject();
    responseProject.setId(project.getId());
    responseProject.setName(project.getName());
    responseProject.setIconUrl(project.getIconUrl());
    responseProject.setDescription(project.getDescription());
    responseProject.setUserIds(
        // project.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList())
        project.getMembers().stream().map(member -> member.getUser().getId())
          .collect(Collectors.toList()));
    return responseProject;
  }
}