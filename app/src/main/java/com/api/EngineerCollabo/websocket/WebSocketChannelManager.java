package com.api.EngineerCollabo.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketChannelManager {
    // チャンネル名とそのチャンネルに参加しているセッションのマッピングを保持するマップ
    private Map<String, Set<String>> channelSessionsMap;

    private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();

    public void setSessions(WebSocketSession session) {
      boolean isExistSession =  this.sessions.stream().anyMatch(value -> value == session);
      if (!isExistSession) {
        this.sessions.add(session);
      }
    }

    public List<WebSocketSession> getSessions() {
      return this.sessions;
    }

    public WebSocketChannelManager() {
        this.channelSessionsMap = new HashMap<>();
    }

    // チャンネルにセッションを追加するメソッド
    public void addSessionToChannel(String channelId, String sessionId) {
        Set<String> sessions = channelSessionsMap.getOrDefault(channelId, new HashSet<>());
        sessions.add(sessionId);
        channelSessionsMap.put(channelId, sessions);
    }

    // チャンネルからセッションを削除するメソッド
    public void removeSessionFromChannel(String channelId, String sessionId) {
        Set<String> sessions = channelSessionsMap.getOrDefault(channelId, new HashSet<>());
        sessions.remove(sessionId);
        channelSessionsMap.put(channelId, sessions);
    }

    // 特定のチャンネルに参加しているセッションのリストを取得するメソッド
    public Set<String> getSessionsInChannel(String channelId) {
        return channelSessionsMap.getOrDefault(channelId, new HashSet<>());
    }

    // 全てのチャンネルとそれに参加しているセッションのマッピングを取得するメソッド
    public Map<String, Set<String>> getAllChannelSessions() {
        return new HashMap<>(channelSessionsMap);
    }

  //   private void sendMessage(String sessionId, String message) {
  //     WebSocketSession session = /* ここでセッションを取得するコードを追加する */;
  //     if (session != null && session.isOpen()) {
  //         try {
  //             session.sendMessage(new TextMessage(message));
  //         } catch (IOException e) {
  //             // エラー処理
  //         }
  //     }
  // }
}
