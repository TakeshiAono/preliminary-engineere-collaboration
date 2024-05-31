package com.api.EngineerCollabo.entities;

import java.util.Date;
import java.util.List;
import lombok.Data;
import com.fasterxml.jackson.databind.JsonNode;

@Data
public class ResponseProject {
    private Integer id;

    private String name;

    private String iconUrl;

    private String description;

    private String recruitingText;

    private JsonNode useTechnology;

    private JsonNode recruitingMemberJob;

    private Date deadline;

    private Integer ownerId;

    private List<Integer> userIds;

    private List<Integer> ownerIds;

    private List<Integer> projectNoticeIds;

    private List<Integer> directoryIds;

    private List<Integer> chatRoomIds;

    private List<Integer> operationIds;

    private List<Integer> taskIds;
}