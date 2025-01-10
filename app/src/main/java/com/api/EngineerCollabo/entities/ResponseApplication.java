package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class ResponseApplication {
    private Integer id;

    private String message;

    private Integer userId;

    private Integer projectId;

    private String userName;

    private String projectName; 

    private Boolean isAccepted;

}