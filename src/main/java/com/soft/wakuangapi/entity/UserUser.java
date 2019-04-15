package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class UserUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer concernId;

    private Integer userId;//关注者
    private Integer concerneduserId;//被关注者
}
