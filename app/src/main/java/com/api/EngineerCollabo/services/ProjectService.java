package com.api.EngineerCollabo.services;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
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
        project.getMembers().stream().map(member -> member.getUser().getId())
          .collect(Collectors.toList())
    );
    return responseProject;
  }
}