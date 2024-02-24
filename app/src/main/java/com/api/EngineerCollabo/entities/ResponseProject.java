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

}