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

import com.api.EngineerCollabo.entities.ChatRoom;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseChatRoom;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.ChatRoomRepository;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

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