package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.EngineerCollabo.entities.ChatMessage;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Integer> {

    // ChatMessage findById(int id);

}