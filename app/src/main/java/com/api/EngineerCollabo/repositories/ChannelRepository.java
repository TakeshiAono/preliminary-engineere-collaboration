package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    Channel findById(int id);

    List<Channel> findByOwner(User owner);

    Channel findByMessages(Message message);

    List<Channel> findByProjectId(Integer projectId);
}