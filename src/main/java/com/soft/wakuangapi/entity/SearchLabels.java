package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class SearchLabels {
    private String key;
    private Integer userId;

    public SearchLabels() {
    }
}
