package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class ResponseMessage {
    private Integer id;

    private String text;

    private String content;

    private Integer userId;

    private Integer channelId;

}