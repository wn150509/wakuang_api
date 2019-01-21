package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Events {
    @Id
    @GeneratedValue
    private Integer eventsId;

    private String eventsUrl;
    private String eventsTitle;
    private Date eventsTime;
    private String eventsPlace;
    private String joinDoor;
    private Integer typeId;
}
