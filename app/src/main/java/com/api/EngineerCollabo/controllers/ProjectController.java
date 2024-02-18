package com.api.EngineerCollabo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/projects")
public class ProjectController {


    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    @RequestMapping("/{id}")
    public Project responseProject(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Project project = projectRepository.findById(id);
            return project;
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putProject(@PathVariable("id") Optional<Integer> ID, @RequestBody Project requestProject) {
        if (ID.isPresent()) {
            int id = ID.get();
            Project project = projectRepository.findById(id);

            String name = requestProject.getName();
            if (name != null) {
                project.setName(name);
            }

            String iconUrl = requestProject.getIconUrl();
            if (iconUrl != null) {
                project.setIconUrl(iconUrl);
            }

            String description = requestProject.getDescription();
            if (description != null) {
                project.setDescription(description);
            }
            projectRepository.save(project);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            projectRepository.deleteById(id);
        }
    }

    @PostMapping("/create")
    public void createProject(@RequestBody Project requestProject) {
        projectRepository.save(requestProject);
    }

    @GetMapping("/search")
    public List<Project> createProject(@RequestParam(value = "name", required = false, defaultValue = "") String name, @RequestParam(value = "description", required = false, defaultValue = "") String description) {
        // TODO: likeのand検索ができるライブラリがありそうなのでそれを使う。
        if(name != null && description != null) {
            List<Project> projectList = projectRepository.findByNameLike("%"+ name +"%");
            return projectList.stream().filter(project -> project.getDescription().contains(description)).collect(Collectors.toList());
        } else if(name != null) {
            List<Project> projectList = projectRepository.findByNameLike("%"+ name +"%");
            return projectList;
        } else if(description != null) {
            List<Project> projectList = projectRepository.findByDescriptionLike("%"+ description +"%");
            return projectList;
        } else {
            return new ArrayList<>();
        }
    }
}
