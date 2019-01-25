package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Topics {
    @Id
    @GeneratedValue
    private Integer topicId;

    private String topicName;
    private String topicUrl;
    private String topicDescription;
}
