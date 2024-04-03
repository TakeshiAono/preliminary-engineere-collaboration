package com.api.EngineerCollabo.services;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.api.EngineerCollabo.entities.ChatMessage;
import com.api.EngineerCollabo.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void saveChatMessage(ChatMessage chatMessage) {
        // メッセージをデータベースに保存する処理
        chatRepository.save(chatMessage);
    }

    public List<ChatMessage> getAllChatMessages() {
        // データベースからチャットの履歴を取得する処理
        return chatRepository.findAll();
    }
}
