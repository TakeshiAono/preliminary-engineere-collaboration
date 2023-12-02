package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    Channel findById(int id);

    List<Channel> findByUser(User user);

    Channel findByMessages(Message message);
}