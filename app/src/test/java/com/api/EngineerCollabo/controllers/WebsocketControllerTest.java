package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.WebSocketRequestObject;
import com.api.EngineerCollabo.repositories.MessageRepository;
import com.api.EngineerCollabo.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class WebsocketControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private WebsocketController websocketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMessage() throws Exception {
        // モックの設定
        WebSocketRequestObject webSocketRequestObject = new WebSocketRequestObject();
        webSocketRequestObject.setMessage("Hello");
        webSocketRequestObject.setContent("Test content");
        webSocketRequestObject.setUserId("1");
        webSocketRequestObject.setChannelId("2");

        // 実行
        websocketController.saveMessage(webSocketRequestObject);

        // 検証
        verify(messageService, times(1)).createMessage("Hello", "Test content", 1, 2);
    }
}
