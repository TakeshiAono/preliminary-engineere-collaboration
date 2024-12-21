package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Application;
import com.api.EngineerCollabo.entities.ResponseApplication;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.repositories.ApplicationRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.services.UserNoticeService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ApplicationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserNoticeService userNoticeService;

    public Application createApplication(String message, Integer userId, Integer projectId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found"));

        // ユーザーが既にプロジェクトのメンバーかチェック
        if (project.getUsers().contains(user)) {
            throw new IllegalStateException("既にプロジェクトのメンバーです");
        }

        Application application = new Application();
        application.setMessage(message);
        application.setUserId(user.getId());
        application.setProjectId(project.getId());

        Application savedApplication = applicationRepository.save(application);

        // Applicationを受信したPJオーナーに通知を作成
        userNoticeService.createApplicationReceivedNotice(project.getOwner().getId(), savedApplication.getId());

        return savedApplication;
    }

    public ResponseApplication changResponseApplication(Application application) {
        ResponseApplication responseApplication = new ResponseApplication();
        responseApplication.setId(application.getId());
        responseApplication.setMessage(application.getMessage());
        responseApplication.setUserId(application.getUser().getId());
        responseApplication.setProjectId(application.getProject().getId());

        // プロジェクト名とユーザー名を設定
        responseApplication.setUserName(application.getUser().getName());
        responseApplication.setProjectName(application.getProject().getName());
        
        return responseApplication;
    }

    public void acceptApplication(Integer applicationId) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        User user = application.getUser();
        Project project = application.getProject();

        // プロジェクトにユーザーが既に参加していないか確認
        if (!project.getUsers().contains(user)) {
            project.getUsers().add(user);
            projectRepository.save(project);  // プロジェクトを保存
        }
        application.setIsAccepted(true); 
        applicationRepository.save(application);
    }

}
