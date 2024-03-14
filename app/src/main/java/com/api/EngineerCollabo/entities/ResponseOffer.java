package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class ResponseOffer {
    private Integer id;

    private String text;

    private Integer userId;

    private Integer scoutedUserId;

}