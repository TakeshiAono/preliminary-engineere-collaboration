package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.ChatRoom;
import com.api.EngineerCollabo.entities.Project;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findById(int id);

    ChatRoom findByName(String name);

    List<ChatRoom> findByProject(Project project);
}