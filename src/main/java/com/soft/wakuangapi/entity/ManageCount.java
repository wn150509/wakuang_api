package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class ManageCount {
    private Integer userCount;
    private Integer pinsCount;
    private Integer articlesCount;

    public ManageCount(Integer userCount, Integer pinsCount, Integer articlesCount) {
        this.userCount = userCount;
        this.pinsCount = pinsCount;
        this.articlesCount = articlesCount;
    }

    public ManageCount() {
    }
}
