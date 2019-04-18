package com.soft.wakuangapi.entity;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class UserType {
    private Integer userId;
    private Integer typeId;

    public UserType() {
    }
}
