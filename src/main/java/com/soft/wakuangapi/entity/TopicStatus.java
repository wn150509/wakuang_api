package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class TopicStatus {
    private Integer topicId;
    private String topicUrl;
    private String topicName;
    private String topicDescription;
    private Integer pinsCount;
    private Integer fansCount;
    private Integer status;

    public TopicStatus() {
    }

    public TopicStatus(Integer topicId, String topicUrl, String topicName, String topicDescription,Integer fansCount,Integer pinsCount, Integer status) {
        this.topicId = topicId;
        this.topicUrl = topicUrl;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.pinsCount = pinsCount;
        this.fansCount = fansCount;
        this.status = status;
    }
}
