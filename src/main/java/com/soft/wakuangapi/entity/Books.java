package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Books {
    @Id
    @GeneratedValue
    private Integer booksId;

    private String booksTitle;
    private String booksAvatar;
    private String booksInfo;
    private String authorName;
    private String authorAvatar;
    private String authorPosition;
    private Integer userId;
    private String booksPrice;
    private Integer catelogId;
    private Integer typeId;


    public Books() {
    }
}
