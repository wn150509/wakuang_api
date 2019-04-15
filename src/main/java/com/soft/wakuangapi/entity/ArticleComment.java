package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class ArticleComment {
    @Id
    @GeneratedValue
    private Integer commentId;

    private Integer articleId;
    private Integer userId;//评论的用户的id

    private String commentContent;
    private Date commentTime;
}
