package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class UserRightMessageCount {
    private Integer pinCount;//沸点数
    private Integer articleCount;//专栏数
    private Integer userConcernCount;//用户关注了作者数量
    private Integer userConcernedCount;//用户受关注数
    private Integer likeCount;//被点赞数
    private Integer commentCount;//被评论总数
    private Integer concernLabel;//关注标签数
    private Integer concernTopic;//关注话题数量

    public UserRightMessageCount() {
    }

    public UserRightMessageCount(Integer pinCount, Integer articleCount, Integer userConcernCount, Integer userConcernedCount, Integer likeCount, Integer commentCount, Integer concernLabel, Integer concernTopic) {
        this.pinCount = pinCount;
        this.articleCount = articleCount;
        this.userConcernCount = userConcernCount;
        this.userConcernedCount = userConcernedCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.concernLabel = concernLabel;
        this.concernTopic = concernTopic;
    }
}
