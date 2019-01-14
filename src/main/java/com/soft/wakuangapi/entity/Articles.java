package com.soft.wakuangapi.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Articles {
    @Id
    @GeneratedValue
    private Integer articleId;
    private String articleTitle;
    private String articleContent;
    private String articleAuthor;
    private String articlePic;
    private Integer commentCount;
    private Integer likeCount;
    private String authorAvatar;

    private Integer usersId;
    private Integer labelId;
    private Date createTime;
    private Integer typeId;

    public Articles() {

    }
}
