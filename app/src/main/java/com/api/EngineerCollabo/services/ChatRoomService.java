package com.api.EngineerCollabo.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.ChatRoom;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseChatRoom;
import com.api.EngineerCollabo.repositories.ChatRoomRepository;
import com.api.EngineerCollabo.repositories.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ChatRoomService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public ChatRoom createChatRoom(String name, Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setProjectId(project.getId());
        return chatRoomRepository.save(chatRoom);
    }

    public ResponseChatRoom changeResponseChatRoom(ChatRoom chatRoom) {
        ResponseChatRoom responseChatRoom = new ResponseChatRoom();
        responseChatRoom.setId(chatRoom.getId());
        responseChatRoom.setName(chatRoom.getName());
        responseChatRoom.setProjectId(chatRoom.getProject().getId());
        responseChatRoom.setChannelIds(
                chatRoom.getChannels().stream().map(channel -> channel.getId()).collect(Collectors.toList()));
        return responseChatRoom;
    }
}