package com.api.EngineerCollabo.entities;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ResponseProjectTasks {
    private Integer projectId;

    private List<Task> tasks;

}