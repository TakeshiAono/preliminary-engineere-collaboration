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

import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ChannelService;
import com.api.EngineerCollabo.entities.ResponseChannel;
import com.api.EngineerCollabo.entities.Channel;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelService channelService;

    @PostMapping("/create")
    public void createChannel(@RequestBody Channel requestChannel) {
        String name = requestChannel.getName();
        Integer userId = requestChannel.getUserId();
        Integer chatRoomId = requestChannel.getChatRoomId();

        if(userId != null){
            channelService.createChannel(name, userId, chatRoomId);
        }
    }

    @GetMapping("/{id}")
    public ResponseChannel responseChannel(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Channel channel = channelRepository.findById(id);
            return channelService.changeResponseChannel(channel);
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putChanel(@PathVariable("id") Optional<Integer> ID, @RequestBody Channel requestChannel) {
        if (ID.isPresent()) {
            int id = ID.get();
            Channel channel = channelRepository.findById(id);

            String name = requestChannel.getName();
            if (name != null) {
                channel.setName(name);
            }
            channelRepository.save(channel);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            channelRepository.deleteById(id);
        }
    }
}
