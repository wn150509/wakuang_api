package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.Topics;
import lombok.Data;

@Data
public class TopicManageVo {
    private Topics topics;
    private Integer concernCount;
    private Integer pinCount;

    public TopicManageVo(Topics topics, Integer concernCount, Integer pinCount) {
        this.topics = topics;
        this.concernCount = concernCount;
        this.pinCount = pinCount;
    }

    public TopicManageVo() {
    }
}
