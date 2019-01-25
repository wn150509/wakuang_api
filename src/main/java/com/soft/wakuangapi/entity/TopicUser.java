package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class TopicUser{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer topicuserId;

    private Integer userId;
    private Integer topicId;
}

