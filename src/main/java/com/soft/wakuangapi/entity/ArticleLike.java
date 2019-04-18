package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer likeId;

    private Integer articleId;
    private Integer userId;

    public ArticleLike() {
    }
}
