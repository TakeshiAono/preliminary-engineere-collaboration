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

    private List<Integer> followerIds;

    private List<Integer> skillIds;

    private List<Integer> roleIds;

    private List<Integer> channelIds;

    private List<Integer> messageIds;

    private List<Integer> offerIds;

    private List<Integer> userNoticeIds;

    private List<Integer> projectIds;
}
