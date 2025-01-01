package com.api.EngineerCollabo.entities;
import lombok.Data;

@Data
public class DeleteChannelRequest {
  private Integer ownerId;

  public Integer getOwnerId() {
      return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
      this.ownerId = ownerId;
  }
}