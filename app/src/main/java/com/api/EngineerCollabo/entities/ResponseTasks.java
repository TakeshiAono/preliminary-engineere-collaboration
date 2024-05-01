package com.api.EngineerCollabo.entities;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ResponseTasks {
    private Integer projectId;

    private Integer userId;

    private List<Task> tasks;

}