package com.api.EngineerCollabo.websocket;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.api.EngineerCollabo.controllers.WebsocketController;
import com.api.EngineerCollabo.entities.WebSocketRequestObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WebSocketのHandler
 */
// @Component("customMessageHandler")
public class MessageHandler extends TextWebSocketHandler {

    @Autowired
    private WebsocketController websocketController;

    // @Autowired
    private WebSocketChannelManager webSocketChannelManager = new WebSocketChannelManager();

    // @Autowired
    // private WebSocketMessageSender webSocketMessageSender;
    /**
     * 接続確立
     */

    private String myId;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.myId = session.getId();
        System.out.println("WebSocketの接続が確立しました。セッションIDは" + session.getId() + "です");
        System.out.println("セッション" + session);
        TextMessage outputMessage = new TextMessage(this.myId);
        session.sendMessage(outputMessage);
    }
    /**
     * メッセージの送受信
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketRequestObject messageObject = null;
        String userId = "";
        String channelId = "";
        String message = "";
        String content = "";
        try {
            messageObject = objectMapper.readValue(requestBody.getPayload(), WebSocketRequestObject.class);
            userId = messageObject.getUserId();
            channelId = messageObject.getChannelId();
            message = messageObject.getMessage();
            content = messageObject.getContent();
            System.out.println("ReceiveRequestCode" + " UserID:" + userId + " ChannelID:" + channelId + " message:" + message + " content:" + content);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // or logger.error("Error processing JSON", e);
        }
        this.webSocketChannelManager.setSessions(session);
        this.webSocketChannelManager.addSessionToChannel(channelId, session.getId());
        Set<String> sessionIds = this.webSocketChannelManager.getSessionsInChannel(channelId);
        // TODO: 受け取ったものをそのまま同一channelのuserに返信する。、
        TextMessage outputMessage = new TextMessage(requestBody.getPayload());
        try {
            this.websocketController.saveMessage(messageObject);
        } catch (Exception e) {
            // 例外を処理するコードを記述するか、例外を再スローする
            e.printStackTrace(); // 例外のスタックトレースを出力するなど
        }
        // this.messageController.saveMessage(messageObject);

        Set<String> currentChannelSessionIds = this.webSocketChannelManager.getSessionsInChannel(channelId);
        System.out.println(this.webSocketChannelManager.getSessions());
        this.webSocketChannelManager.getSessions().stream().forEach(sessiona -> {
            if(currentChannelSessionIds.contains(sessiona.getId())){
                try {
                    sessiona.sendMessage(outputMessage);
                    System.err.println("Broad Cast Request: " + outputMessage.getPayload());
                } catch (IOException e) {
                    System.err.println("Failed to send requestBody: " + e.getMessage());
                    throw new RuntimeException("Failed to send requestBody", e);
                }
            }
        });
    }
    /**
     * 接続終了
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocketの接続が終了しました。");
    }
}
