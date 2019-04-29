package com.soft.wakuangapi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PinCommentStatus {
    private Integer commentId;

    private Integer pinId;
    private UserStatus userStatus;//评论的用户

    private String commentContent;
    private Date commentTime;
    private Integer likeCount;
    private Integer status;

    public PinCommentStatus() {
    }

    public PinCommentStatus(Integer commentId, Integer pinId, UserStatus userStatus, String commentContent, Date commentTime, Integer likeCount, Integer status) {
        this.commentId = commentId;
        this.pinId = pinId;
        this.userStatus = userStatus;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.likeCount = likeCount;
        this.status = status;
    }
}
