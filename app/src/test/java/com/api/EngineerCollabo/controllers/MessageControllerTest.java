package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.ResponseMessage;
import com.api.EngineerCollabo.repositories.MessageRepository;
import com.api.EngineerCollabo.services.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @Test
    void createMessage() {
        // 準備
        Message requestMessage = new Message();
        requestMessage.setText("Test Text");
        requestMessage.setContent("Test Content");
        requestMessage.setUserId(1);
        requestMessage.setChannelId(1);

        // 実行
        messageController.createMessage(requestMessage);

        // 検証
        verify(messageService, times(1)).createMessage("Test Text", "Test Content", 1, 1);
    }

    @Test
    void responseMessage() {
        // 準備
        int messageId = 1;
        Message message = new Message();
        message.setId(messageId);

        ResponseMessage expectedResponse = new ResponseMessage();
        expectedResponse.setId(messageId);

        // モックの設定
        when(messageRepository.findById(messageId)).thenReturn(message);
        when(messageService.changeResponseMessage(message)).thenReturn(expectedResponse);

        // 実行
        ResponseMessage actualResponse = messageController.responseMessage(Optional.of(messageId));

        // 検証
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void putMessage() {
        // 準備
        int messageId = 1;
        Message message = new Message();
        message.setId(messageId);
        message.setText("Original Text");

        Message requestMessage = new Message();
        requestMessage.setText("Updated Text");
        requestMessage.setContent("Updated Content");

        // モックの設定
        when(messageRepository.findById(messageId)).thenReturn(message);

        // 実行
        messageController.putMessage(Optional.of(messageId), requestMessage);

        // 検証
        verify(messageRepository, times(1)).save(message);
        assertEquals("Updated Text", message.getText());
        assertEquals("Updated Content", message.getContent());
    }

    @Test
    void deleteMessage() {
        // 準備
        int messageId = 1;

        // モックの設定
        doNothing().when(messageRepository).deleteById(messageId);

        // 実行
        messageController.deleteMessage(Optional.of(messageId));

        // 検証
        verify(messageRepository, times(1)).deleteById(messageId);
    }
}
