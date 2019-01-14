package com.soft.wakuangapi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class EventsStatus {
    private String eventsId;

    private String eventsUrl;
    private String eventsTitle;
    private Date eventsTime;
    private String eventsPlace;
    private Integer typeId;
    private Integer status;
}
