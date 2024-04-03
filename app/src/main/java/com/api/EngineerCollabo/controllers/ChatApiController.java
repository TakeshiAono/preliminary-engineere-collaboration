package com.api.EngineerCollabo.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.EngineerCollabo.entities.ChatMessage;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.api.EngineerCollabo.services.ChatService;
import com.api.EngineerCollabo.repositories.ChatRepository;

@RestController
@RequestMapping("/api/messages")
public class ChatApiController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatService chatService;

    @PostMapping
    public ResponseEntity<?> sendChatMessage(@RequestBody ChatMessage chatMessage) {
        // メッセージを処理して保存するなどの処理を行う
        chatRepository.save(chatMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<ChatMessage> getChatMessages() {
        // チャットの履歴を取得する処理を行う
        return chatService.getAllChatMessages();
    }
}

