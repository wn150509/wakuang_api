package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class MessageCount {
    private Integer pinCount;
    private Integer articleCount;
    private Integer userConcernCount;
    private Integer userConcernedCount;

    public MessageCount() {
    }

    public MessageCount(Integer pinCount, Integer articleCount, Integer userConcernCount, Integer userConcernedCount) {
        this.pinCount = pinCount;
        this.articleCount = articleCount;
        this.userConcernCount = userConcernCount;
        this.userConcernedCount = userConcernedCount;
    }
}
