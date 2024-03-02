package com.api.EngineerCollabo.entities;

import java.util.List;
import lombok.Data;

@Data
public class ResponseUser {
    private Integer id;

    private String name;

    private String email;

    private String password;

    private String iconUrl;
    
    private String introduce;

    private List<Integer> userIds;

    // private List<Integer> projectIds;

    // private List<Integer> followerIds;

}


