package com.soft.wakuangapi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PinStatus {
    private Integer pinId;
    private String pinContent;
    private String pinUrl;
    private Integer commentCount;
    private Integer likeCount;

    private Integer usersId;
    private Date createTime;
    private Integer topicId;
    private Integer status;

    public PinStatus(Integer pinId, String pinContent, String pinUrl, Integer commentCount, Integer likeCount, Integer usersId, Date createTime, Integer topicId, Integer status) {
        this.pinId = pinId;
        this.pinContent = pinContent;
        this.pinUrl = pinUrl;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.usersId = usersId;
        this.createTime = createTime;
        this.topicId = topicId;
        this.status = status;
    }

    public PinStatus() {
    }
}
