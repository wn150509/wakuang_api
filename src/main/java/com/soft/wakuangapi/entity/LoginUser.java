package com.soft.wakuangapi.entity;

import lombok.Data;

/**
 * LoginUser DTO
 */
@Data
public class LoginUser {
    private String email;
    private String password;

    public LoginUser() {
    }
}
