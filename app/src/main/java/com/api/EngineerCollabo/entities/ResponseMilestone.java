package com.api.EngineerCollabo.entities;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ResponseMilestone {
    private Integer id;

    private String name;

    private Date deadline;
}
