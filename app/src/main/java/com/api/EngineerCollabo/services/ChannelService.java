package com.api.EngineerCollabo.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.ResponseChannel;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ChannelService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    public Channel createChannel(String name, Integer ownerId, Integer projectId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Channel channel = new Channel();

        channel.setName(name);
        channel.setOwnerId(owner.getId());
        channel.setProjectId(projectId);

        return channelRepository.save(channel);
    }

    public ResponseChannel changeResponseChannel(Channel channel) {
        ResponseChannel responseChannel = new ResponseChannel();
        responseChannel.setId(channel.getId());
        responseChannel.setName(channel.getName());
        responseChannel.setOwnerId(channel.getOwnerId());
        return responseChannel;
    }
}