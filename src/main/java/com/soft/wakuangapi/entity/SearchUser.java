package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class SearchUser {
    private String key;
    private Integer userId;

    public SearchUser() {
    }
}
