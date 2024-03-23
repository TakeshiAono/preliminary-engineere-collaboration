package com.api.EngineerCollabo.entities;

import lombok.Data;

@Data
public class ResponseFile {
    private Integer id;

    private String name;

    private String fileUrl;

    private Integer directoryId;

}