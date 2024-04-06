package com.api.EngineerCollabo.entities;

import java.util.Date;

import lombok.Data;

@Data
public class ResponseTask {
    private Integer id;

    private String name;

    private Boolean isDone;

    private Date deadline;

    private String description;

    private Integer projectId;

    private Integer inChargeUserId;

    private Date createdAt;

    private Date updatedAt;

}