package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class TopicPinCount {
    private String name;
    private Integer value;

    public TopicPinCount(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public TopicPinCount() {
    }
}
