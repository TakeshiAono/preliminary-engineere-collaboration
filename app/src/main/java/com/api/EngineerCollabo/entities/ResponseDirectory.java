package com.api.EngineerCollabo.entities;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDirectory {
    private Integer id;

    private String name;

    private Integer projectId;

    private List<Integer> fileIds;
}