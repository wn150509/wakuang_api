package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class UserStatus {
    private Integer userId;
    private String userAvatar;
    private String userName;
    private String description;
    private String userCompany;
    private String userPosition;
    private Integer status;

    public UserStatus() {
    }

    public UserStatus(Integer userId, String userAvatar, String userName, String description, String userCompany, String userPosition, Integer status) {
        this.userId = userId;
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.description = description;
        this.userCompany = userCompany;
        this.userPosition = userPosition;
        this.status = status;
    }
}
