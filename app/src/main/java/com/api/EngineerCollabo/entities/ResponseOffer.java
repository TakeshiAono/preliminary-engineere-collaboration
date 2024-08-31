package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class ResponseOffer {
    private Integer id;

    private String message;

    private Integer userId;

    private Integer scoutedUserId;

    private Integer projectId;

    private String userName; // 追加
    private String projectName; // 追加

}