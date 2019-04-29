package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class MessageCount {
    private Integer pinCount;//沸点数
    private Integer articleCount;//专栏数
    private Integer userConcernCount;//用户关注了数量
    private Integer userConcernedCount;//用户受关注数

    public MessageCount() {
    }

    public MessageCount(Integer pinCount, Integer articleCount, Integer userConcernCount, Integer userConcernedCount) {
        this.pinCount = pinCount;
        this.articleCount = articleCount;
        this.userConcernCount = userConcernCount;
        this.userConcernedCount = userConcernedCount;
    }
}
