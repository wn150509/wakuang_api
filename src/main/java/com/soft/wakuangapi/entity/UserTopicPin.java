package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class UserTopicPin {
    private Integer userId;
    private Integer concerneduserId;
    private Integer topicId;

    public UserTopicPin() {
    }
}
