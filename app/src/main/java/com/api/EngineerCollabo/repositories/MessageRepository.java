package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findById(int id);

    List<Message> findByUser(User user);

    List<Message> findByChannel(Channel channel);
}