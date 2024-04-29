package com.api.EngineerCollabo.entities;

import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class RequestMessage {
    private String userId;
    private String channelId;
    private String message;
    private String content;
}
