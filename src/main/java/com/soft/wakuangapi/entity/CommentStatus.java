package com.soft.wakuangapi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CommentStatus {
    private Integer commentId;

    private Integer articleId;
    private UserStatus userStatus;//评论的用户

    private String commentContent;
    private Date commentTime;
    private Integer likeCount;
    private Integer status;

    public CommentStatus() {
    }

    public CommentStatus(Integer commentId, Integer articleId, UserStatus userStatus, String commentContent, Date commentTime, Integer likeCount, Integer status) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.userStatus = userStatus;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.likeCount = likeCount;
        this.status = status;
    }
}
