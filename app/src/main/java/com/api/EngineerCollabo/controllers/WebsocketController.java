package com.api.EngineerCollabo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.api.EngineerCollabo.entities.WebSocketRequestObject;
import com.api.EngineerCollabo.repositories.MessageRepository;
import com.api.EngineerCollabo.services.MessageService;

@Configuration
public class WebsocketController {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;

    public void saveMessage(WebSocketRequestObject webSocketRequestObject) throws Exception {
        String text = webSocketRequestObject.getMessage();
        String content = webSocketRequestObject.getContent();
        Integer userId = Integer.parseInt(webSocketRequestObject.getUserId());
        Integer channelId = Integer.parseInt(webSocketRequestObject.getChannelId());

        if (text != "" || content != "") {
            messageService.createMessage(text, content, userId, channelId);
        }
    }
}
