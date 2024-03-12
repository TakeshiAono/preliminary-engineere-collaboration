package com.api.EngineerCollabo.services;

import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.ResponseMessage;

@Service
public class MessageService {

    public ResponseMessage changeResponseMessage(Message message) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setId(message.getId());
        responseMessage.setText(message.getText());
        responseMessage.setContent(message.getContent());
        responseMessage.setUserId(message.getUser().getId());
        responseMessage.setChannelId(message.getChannel().getId());
        return responseMessage;
    }
}