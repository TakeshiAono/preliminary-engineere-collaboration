package com.api.EngineerCollabo.entities;

import java.util.List;
import lombok.Data;

@Data
public class ResponseProject {
    private Integer id;

    private String name;

    private String iconUrl;

    private String description;

    private List<Integer> userIds;

    private List<Integer> ownerIds;

    private List<Integer> projectNoticeIds;

    private List<Integer> directoryIds;

    private List<Integer> fileIds;

    private List<Integer> chatRoomIds;
}