package com.api.EngineerCollabo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseProject;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ProjectService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserRepository userRepository;

    // @Autowired
    // MemberRepository memberRepository;

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

    @GetMapping
    public List<ResponseProject> responseProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map((project) -> projectService.changeResponseProject(project)).toList();
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

            Date deadline = requestProject.getDeadline();
            if (deadline != null) {
                project.setDeadline(deadline);
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

    // @PostMapping("/{id}/members/add")
    // public void addMember(@PathVariable("id") Optional<Integer> ID, @RequestBody RequestMembers requestMembers) {
    //     if (ID.isPresent()) {
    //         int id = ID.get();
    //         Project project = projectRepository.findById(id);
    //         List<Integer> userIds = requestMembers.getUserIds();
    //         List<Member> joinedMembers = memberRepository.findByProjectId(id);

    //         // TODD: 以下の処理をserviceに移設しトランザクション化する。userがnullの場合にエラーをthrowしrollbackするようにする。
    //         for(Integer userId : userIds) {
    //             Member member = new Member();
    //             Optional<User> optionalUser = userRepository.findById(userId);
    //             if(!optionalUser.isPresent()) {return;}
    //             boolean isUserExist = joinedMembers.stream().anyMatch((memberItem) -> memberItem.getUser().getId() == userId);
    //             if(!isUserExist) {
    //                 member.setProject(project);
    //                 member.setUser(optionalUser.get());
    //                 memberRepository.save(member);
    //             }
    //         }
    //     }
    // }

    // @PatchMapping("/{id}/members/modify")
    // public void modifyMember(@PathVariable("id") Optional<Integer> ID, @RequestBody RequestMembers requestMembers) {
    //     // if(requestMembers.getUserIds().isEmpty()) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userIdsが空です。");}
    //     if (ID.isPresent()) {
    //         int id = ID.get();
    //         List<Integer> userIds = requestMembers.getUserIds();
    //         for(Integer userId : userIds) {
    //             Optional<User> optionalUser = userRepository.findById(userId);
    //             if(!optionalUser.isPresent()) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "存在しないユーザーをメンバーにはできません。");}
    //         }
    //         Project project = projectRepository.findById(id);
    //         List<Member> joinedMembers = memberRepository.findByProjectId(id);
    //         joinedMembers.stream().forEach(joinedMemberItem -> memberRepository.deleteById(joinedMemberItem.getId()));

    //         // TODD: 以下の処理をserviceに移設しトランザクション化する。userがnullの場合にエラーをthrowしrollbackするようにする。
    //         for(Integer userId : userIds) {
    //             Member member = new Member();
    //             System.out.println(userId);
    //             Optional<User> optionalUser = userRepository.findById(userId);
    //             if(!optionalUser.isPresent()) {return;}
    //             member.setProject(project);
    //             member.setUser(optionalUser.get());
    //             memberRepository.save(member);
    //         }
    //     }
    // }
}
