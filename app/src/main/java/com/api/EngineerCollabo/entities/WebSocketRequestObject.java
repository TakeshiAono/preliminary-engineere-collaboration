package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class WebSocketRequestObject {
    private String userId;
    private String channelId;
    private String message;
    private String content;
}
