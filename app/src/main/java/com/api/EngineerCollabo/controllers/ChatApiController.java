package com.api.EngineerCollabo.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.ResponseMessage;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.api.EngineerCollabo.services.MessageService;
import com.api.EngineerCollabo.repositories.MessageRepository;

@RestController
@RequestMapping("/api/messages")
public class ChatApiController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        // メッセージを処理して保存するなどの処理を行う
        String text = message.getText();
        String content = message.getContent();
        Integer userId = message.getUserId();
        Integer channelId = message.getChannelId();

        if(userId !=null && channelId !=null){
            messageService.saveMessage(text, content, userId, channelId);
        }
        return ResponseEntity.ok().build();

    }

    @GetMapping
    public List<ResponseMessage> getAllMessages() {
        // チャットの履歴を取得する処理を行う
        return messageService.getAllMessages();
    }
}

