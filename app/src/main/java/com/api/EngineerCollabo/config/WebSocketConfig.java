package com.api.EngineerCollabo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.sockjs.transport.handler.WebSocketTransportHandler;

import com.api.EngineerCollabo.websocket.MessageHandler;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
/**
 * WebSocketの管理
 */

 @Configuration
 @EnableWebSocket
 public class WebSocketConfig implements WebSocketConfigurer {
   /**
    * WebSocketの登録
    */
   @Override
     public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler(),"/websocket")
        .setAllowedOrigins("*");
     }
    /**
      * WebSocketHandler（コントロールするクラス）を定義
      * @return WebSocketHandler
      */
    @Bean
    public WebSocketHandler messageHandler() {
        return new MessageHandler();
    }
 }
