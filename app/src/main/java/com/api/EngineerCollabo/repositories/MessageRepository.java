package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findById(int id);

    List<Message> findByUser(User user);

    List<Message> findByChannel(Channel channel);
}