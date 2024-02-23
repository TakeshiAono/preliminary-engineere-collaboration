package com.api.EngineerCollabo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.api.EngineerCollabo.entities.Project;
// import com.api.EngineerCollabo.entities.ResponseProject;
// import com.api.EngineerCollabo.repositories.ProjectRepository;
// import com.api.EngineerCollabo.services.ProjectService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import java.util.List;

// import static org.mockito.Mockito.description;

import java.util.ArrayList;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/projects")
public class ProjectController {


    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseProject responseProject(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Project project = projectRepository.findById(id);
            return projectService.changeResponseProject(project);
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
    public List<ResponseProject> searchProject(@RequestParam(value = "name", required = false, defaultValue = "") String name, @RequestParam(value = "description", required = false, defaultValue = "") String description) {
        // TODO: likeのand検索ができるライブラリがありそうなのでそれを使いたい。
        List<Project> projectList = null;
        List<ResponseProject> responseProjectList = new ArrayList<>();
        if(!name.equals("") && !description.equals("")) {
            projectList = projectRepository.findByNameLike("%"+ name +"%");
            projectList = projectList.stream().filter(project -> project.getDescription().contains(description)).collect(Collectors.toList());
        }
        else if(!name.equals("")) {
            projectList = projectRepository.findByNameLike("%"+ name +"%");
        } else if(!description.equals("")) {
            projectList = projectRepository.findByDescriptionLike("%"+ description +"%");
        } else {
            return responseProjectList;
        }

        for (Project project : projectList) {
            responseProjectList.add(projectService.changeResponseProject(project));
        }
        return responseProjectList;
    }
}
