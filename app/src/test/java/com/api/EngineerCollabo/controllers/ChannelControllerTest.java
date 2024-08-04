package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.ResponseChannel;
import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.services.ChannelService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChannelControllerTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private ChannelService channelService;

    @InjectMocks
    private ChannelController channelController;

    @Test
    void createChannel() {
        // 準備
        Channel requestChannel = new Channel();
        requestChannel.setName("Test Channel");
        requestChannel.setUserId(1);
        requestChannel.setChatRoomId(1);

        // 実行
        channelController.createChannel(requestChannel);

        // 検証
        verify(channelService, times(1)).createChannel("Test Channel", 1, 1);
    }

    @Test
    void responseChannel() {
        // 準備
        int channelId = 1;
        Channel channel = new Channel();
        channel.setId(channelId);

        ResponseChannel expectedResponse = new ResponseChannel();
        expectedResponse.setId(channelId);

        // モックの設定
        when(channelRepository.findById(channelId)).thenReturn(channel);
        when(channelService.changeResponseChannel(channel)).thenReturn(expectedResponse);

        // 実行
        ResponseChannel actualResponse = channelController.responseChannel(Optional.of(channelId));

        // 検証
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void putChanel() {
        // 準備
        int channelId = 1;
        Channel channel = new Channel();
        channel.setId(channelId);
        channel.setName("Original Name");

        Channel requestChannel = new Channel();
        requestChannel.setName("Updated Name");

        // モックの設定
        when(channelRepository.findById(channelId)).thenReturn(channel);

        // 実行
        channelController.putChanel(Optional.of(channelId), requestChannel);

        // 検証
        verify(channelRepository, times(1)).save(channel);
        assertEquals("Updated Name", channel.getName());
    }

    @Test
    void deleteChannel() {
        // 準備
        int channelId = 1;

        // 実行
        channelController.deleteChannel(Optional.of(channelId));

        // 検証
        verify(channelRepository, times(1)).deleteById(channelId);
    }
}
