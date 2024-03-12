package com.api.EngineerCollabo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.ResponseMessage;
import com.api.EngineerCollabo.repositories.MessageRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageService messageService;

    @GetMapping("/{id}")
    public ResponseMessage responseMessage(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Message message = messageRepository.findById(id);
            return messageService.changeResponseMessage(message);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putMessage(@PathVariable("id") Optional<Integer> ID, @RequestBody Message requestMessage) {
        if (ID.isPresent()) {
            int id = ID.get();
            Message message = messageRepository.findById(id);

            String text = requestMessage.getText();
            if (text != null) {
                message.setText(text);
            }

            String content = requestMessage.getContent();
            if (content != null) {
                message.setContent(content);
            }
            messageRepository.save(message);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            messageRepository.deleteById(id);
        }
    }

}