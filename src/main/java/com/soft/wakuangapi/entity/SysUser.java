package com.soft.wakuangapi.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * SysUser Entity Class
 * @author Jwang
 * DO
 */
@Entity
@Data
public class SysUser {
    @Id
    @GeneratedValue
    private Integer userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String userName;
    private String userAvatar;
    private String userPosition;
    private String userCompany;
    private String description;
    private Integer likeCount;
    private Integer lookCount;
    private Integer labelId;
    private Integer concernId;
    private Integer topicId;
}
