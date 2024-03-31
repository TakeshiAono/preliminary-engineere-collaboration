package com.api.EngineerCollabo.entities;

import java.util.Date;

import lombok.Data;

@Data
public class ResponseOperation {
    private Integer id;

    private String log;

    private Integer projectId;

    private Date createdAt;

    private Date updatedAt;

}