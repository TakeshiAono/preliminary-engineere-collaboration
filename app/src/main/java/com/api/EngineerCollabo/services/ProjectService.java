package com.api.EngineerCollabo.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.EngineerCollabo.repositories.ProjectRepository;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseProject;

@Service
public class ProjectService {

	@Autowired
    private ProjectRepository projectRepository;

	public ResponseProject changeResponseProject(Project project) {
		ResponseProject responseProject = new ResponseProject();
		responseProject.setId(project.getId());
		responseProject.setRecruitingMemberJob(project.getRecruitingMemberJob());
		responseProject.setRecruitingText(project.getRecruitingText());
		responseProject.setUseTechnology(project.getUseTechnology());
		responseProject.setName(project.getName());
		responseProject.setIconUrl(project.getIconUrl());
		responseProject.setDescription(project.getDescription());
		responseProject.setDeadline(project.getDeadline());
		responseProject.setOwnerId(project.getOwner().getId());
		responseProject.setProjectNoticeIds(
				project.getProjectNotices().stream().map(projectNotice -> projectNotice.getId())
						.collect(Collectors.toList()));
		responseProject.setDirectoryIds(
				project.getDirectories().stream().map(directory -> directory.getId()).collect(Collectors.toList()));
		responseProject.setChatRoomIds(
				project.getChatRooms().stream().map(chatRoom -> chatRoom.getId()).collect(Collectors.toList()));
		responseProject.setUserIds(
				project.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList()));
		responseProject.setOperationIds(
				project.getOperations().stream().map(operation -> operation.getId()).collect(Collectors.toList()));
		responseProject.setTaskIds(
				project.getTasks().stream().map(task -> task.getId()).collect(Collectors.toList()));
		return responseProject;
    }

	public Project getProjectByOfferId(Integer offerId) {
        return projectRepository.findProjectByOfferId(offerId);
    }

	public Project getProjectByApplicationId(Integer applicationId) {
        return projectRepository.findProjectByApplicationId(applicationId);
    }
}