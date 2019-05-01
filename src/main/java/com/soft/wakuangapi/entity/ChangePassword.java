package com.soft.wakuangapi.entity;

import lombok.Data;

@Data
public class ChangePassword {
    private String phone;
    private String newPassword;

    public ChangePassword() {
    }
}
