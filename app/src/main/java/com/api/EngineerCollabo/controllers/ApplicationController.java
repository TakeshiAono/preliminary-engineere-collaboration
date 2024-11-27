package com.api.EngineerCollabo.controllers;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;


import com.api.EngineerCollabo.entities.Application;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseApplication;
import com.api.EngineerCollabo.entities.ResponseProject;
import com.api.EngineerCollabo.repositories.ApplicationRepository;
import com.api.EngineerCollabo.services.ApplicationService;
import com.api.EngineerCollabo.services.ProjectService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<String> createApplication(@RequestBody Application requestApplication) {
        System.out.println("Received application message: " + requestApplication.getMessage());

        String message = requestApplication.getMessage();
        Integer userId = requestApplication.getUserId();
        Integer projectId = requestApplication.getProjectId();

        if (userId != null) {
            applicationService.createApplication(message, userId, projectId);
        return ResponseEntity.ok("Application created successfully");
    } else {
        return ResponseEntity.badRequest().body("Invalid userId");
    }
    }
    
    @PostMapping("/accept/{id}")
    public ResponseEntity<Map<String, Object>> acceptApplication(@PathVariable("id") Integer applicationId) {
        // 受け入れる処理
        applicationService.acceptApplication(applicationId);
        
        // プロジェクトデータを取得
        Project project = projectService.getProjectByApplicationId(applicationId);
        
        // プロジェクトデータをResponseProjectに変換
        ResponseProject responseProject = projectService.changeResponseProject(project);
        
        // レスポンスデータを作成
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User successfully added to project");
        response.put("project", responseProject);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseApplication responseApplication(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Application application = applicationRepository.findById(id);
            
            ResponseApplication responseApplication = applicationService.changResponseApplication(application);
            responseApplication.setIsAccepted(application.getIsAccepted()); // isAcceptedをレスポンスに追加
            return responseApplication;
        } else {
            return null;
        }
    }

    @PatchMapping("{id}")
    public void putApplication(@PathVariable("id") Optional<Integer> ID, @RequestBody Application requestApplication) {
        if (ID.isPresent()) {
            int id = ID.get();
            Application application = applicationRepository.findById(id);

            String message = requestApplication.getMessage();
            if (message != null) {
                application.setMessage(message);
            }

            applicationRepository.save(application);
        }
    }

    @DeleteMapping("{id}")
    public void deleteApplication(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            applicationRepository.deleteById(id);
        }
    }

}
