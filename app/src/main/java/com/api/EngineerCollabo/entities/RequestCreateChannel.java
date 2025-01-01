package com.api.EngineerCollabo.entities;

import java.util.List;

import lombok.Data;

@Data
public class RequestCreateChannel extends Channel {
  private List<Integer> userIds;
}
