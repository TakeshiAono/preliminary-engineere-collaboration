package com.api.EngineerCollabo.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.ChatRoom;
import com.api.EngineerCollabo.entities.ResponseChannel;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.repositories.ChatRoomRepository;
import com.api.EngineerCollabo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ChannelService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChannelRepository channelRepository;

    public Channel createChannel(String name, Integer userId, Integer chatRoomId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("ChatRoom not found"));
        Channel channel = new Channel();
        channel.setName(name);
        channel.setUserId(user.getId());
        channel.setChatRoomId(chatRoom.getId());

        return channelRepository.save(channel);
    }

    public ResponseChannel changeResponseChannel(Channel channel) {
        ResponseChannel responseChannel = new ResponseChannel();
        responseChannel.setId(channel.getId());
        responseChannel.setName(channel.getName());
        responseChannel.setUserId(channel.getUser().getId());
        responseChannel.setChatRoomId(channel.getChatRoom().getId());
        responseChannel.setMessageIds(
                channel.getMessages().stream().map(message -> message.getId()).collect(Collectors.toList()));
        return responseChannel;
    }
}