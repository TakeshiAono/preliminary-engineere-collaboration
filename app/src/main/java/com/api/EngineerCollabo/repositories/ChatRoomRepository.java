package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findById(int id);

    ChatRoom findByName(String name);

    List<ChatRoom> findByProject(Project project);
}