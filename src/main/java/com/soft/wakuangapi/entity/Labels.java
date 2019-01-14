package com.soft.wakuangapi.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Labels{
    @Id
    @GeneratedValue
    private Integer labelsId;

    private String labelsUrl;
    private String labelsName;

    public Labels() {
    }
}
