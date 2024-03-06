package com.api.EngineerCollabo.services;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.ResponseChannel;

@Service
public class ChannelService {

    public ResponseChannel changeResponseChannel(Channel channel) {
        ResponseChannel responseChannel = new ResponseChannel();
        responseChannel.setId(channel.getId());
        responseChannel.setName(channel.getName());
        responseChannel.setUserId(channel.getUser().getId());
        responseChannel.setChatRoomId(channel.getChatRoom().getId());
        responseChannel.setMessageIds(
            channel.getMessages().stream().map(message -> message.getId()).collect(Collectors.toList())
    );
        return responseChannel;
      }
}