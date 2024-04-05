package com.api.EngineerCollabo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.ResponseMessage;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.repositories.MessageRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(String text, String content, Integer userId, Integer channelId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("Channel not found"));
        Message message = new Message();
        message.setText(text);
        message.setContent(content);
        message.setUserId(user.getId());
        message.setChannelId(channel.getId());

        messageRepository.save(message);
    }

    public List<ResponseMessage> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        List<ResponseMessage> responseMessages = new ArrayList<>();
        for (Message message : messages){
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setId(message.getId());
            responseMessage.setText(message.getText());
            responseMessage.setContent(message.getContent());
            responseMessage.setUserId(message.getUser().getId());
            responseMessage.setChannelId(message.getChannel().getId());
            responseMessage.setCreatedAt(message.getCreatedAt());
            responseMessage.setUpdatedAt(message.getUpdatedAt());
            responseMessages.add(responseMessage);
        }
        return responseMessages;
    }


    // public Message createMessage(String text, String content, Integer userId, Integer channelId) {
    //     User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    //     Channel channel = channelRepository.findById(channelId)
    //             .orElseThrow(() -> new EntityNotFoundException("Channel not found"));
    //     Message message = new Message();
    //     message.setText(text);
    //     message.setContent(content);
    //     message.setUserId(user.getId());
    //     message.setChannelId(channel.getId());

    //     return messageRepository.save(message);
    // }

    // public ResponseMessage changeResponseMessage(Message message) {
    //     ResponseMessage responseMessage = new ResponseMessage();
    //     responseMessage.setId(message.getId());
    //     responseMessage.setText(message.getText());
    //     responseMessage.setContent(message.getContent());
    //     responseMessage.setUserId(message.getUser().getId());
    //     responseMessage.setChannelId(message.getChannel().getId());
    //     responseMessage.setCreatedAt(message.getCreatedAt());
    //     responseMessage.setUpdatedAt(message.getUpdatedAt());
    //     return responseMessage;
    // }
}