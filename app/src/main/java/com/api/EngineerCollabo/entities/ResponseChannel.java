package com.api.EngineerCollabo.entities;

import java.util.List;
import lombok.Data;

@Data
public class ResponseChannel {
    private Integer id;

    private String name;

    private Integer ownerId;

    private Integer chatRoomId;
    
    private List<Integer> messageIds;

}