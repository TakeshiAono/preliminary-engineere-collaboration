package com.api.EngineerCollabo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

import com.api.EngineerCollabo.repositories.ChatRoomRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ChatRoomService;
import com.api.EngineerCollabo.entities.ChatRoom;
import com.api.EngineerCollabo.entities.ResponseChatRoom;

@RestController
@RequestMapping("/chatRooms")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatRoomController {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRoomService chatRoomService;

    @PostMapping("/create")
    public void createChatRoom(@RequestBody ChatRoom requestChatRoom) {
        String name = requestChatRoom.getName();
        Integer projectId = requestChatRoom.getProjectId();

        if(projectId != null){
            chatRoomService.createChatRoom(name, projectId);
        }
    }

    @GetMapping("/{id}")
    public ResponseChatRoom responseChatRoom(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            ChatRoom chatRoom = chatRoomRepository.findById(id);
            return chatRoomService.changeResponseChatRoom(chatRoom);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putChanel(@PathVariable("id") Optional<Integer> ID, @RequestBody ChatRoom requestChatRoom) {
        if (ID.isPresent()) {
            int id = ID.get();
            ChatRoom chatRoom = chatRoomRepository.findById(id);

            String name = requestChatRoom.getName();
            Integer projectId = requestChatRoom.getProjectId();
            if (name != null || projectId != null) {
                chatRoom.setName(name);
                chatRoom.setProjectId(projectId);
            }
            chatRoomRepository.save(chatRoom);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteChatRoom(@PathVariable("id") Optional<Integer> ID) {
        System.out.println(ID);
        if (ID.isPresent()) {
            int id = ID.get();
            System.out.println(id);
            chatRoomRepository.deleteById(id);
        }
    }
}