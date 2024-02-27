package com.api.EngineerCollabo.entities;

import java.util.List;

import lombok.Data;

@Data
public class RequestMembers {

    private Integer projectId;

    private List<Integer> userIds;
}
