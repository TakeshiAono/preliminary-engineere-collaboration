package com.api.EngineerCollabo.entities;

import java.util.List;

import lombok.Data;

@Data
public class RequestCreateChannel {
  private String name;
  private Integer ownerId;
  private Integer projectId;
  private List<Integer> userIds;
}
