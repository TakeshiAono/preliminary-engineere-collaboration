package com.api.EngineerCollabo.entities;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.api.EngineerCollabo.controllers.MessageController;

import jakarta.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class WebSocketRequestObject {
    private String userId;
    private String channelId;
    private String message;
}
