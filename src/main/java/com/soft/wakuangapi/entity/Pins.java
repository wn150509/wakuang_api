package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Pins {
    @Id
    @GeneratedValue
    private Integer pinId;
    private String pinContent;
    private String pinUrl;
    private Integer commentCount;
    private Integer likeCount;

    private Integer usersId;
    private Date createTime;
    private Integer topicId;

    public Pins() {
    }
}
