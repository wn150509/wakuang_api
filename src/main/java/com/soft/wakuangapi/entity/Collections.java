package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Collections{
    @Id
    @GeneratedValue
    private Integer collectionsId;

    private String collectionsUrl;
    private String collectionsName;
    private Integer articleCount;
    private Integer fansCount;
    private String userName;
    private String userAvatar;

    private Integer status;
    private Integer userId;
    private Integer typeId;

    public Collections() {
    }
}
