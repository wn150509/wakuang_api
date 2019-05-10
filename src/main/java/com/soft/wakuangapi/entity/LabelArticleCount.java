package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class LabelArticleCount {
    private String  name;
    private Integer value;

    public LabelArticleCount(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public LabelArticleCount() {
    }
}
