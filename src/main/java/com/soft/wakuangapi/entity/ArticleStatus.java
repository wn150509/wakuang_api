package com.soft.wakuangapi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleStatus {
    private Integer articleId;

    private String articleTitle;
    private String articleContent;
    private String articleAuthor;
    private String articlePic;
    private String authorAvatar;
    private Integer commentCount;
    private Integer likeCount;

    private Integer usersId;
    private Integer labelId;
    private Date createTime;
    private Integer typeId;

    private Integer status;

    public ArticleStatus(Integer articleId, String articleTitle, String articleContent, String articleAuthor, String articlePic, String authorAvatar, Integer commentCount, Integer likeCount, Integer usersId, Integer labelId, Date createTime, Integer typeId, Integer status) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleAuthor = articleAuthor;
        this.articlePic = articlePic;
        this.authorAvatar = authorAvatar;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.usersId = usersId;
        this.labelId = labelId;
        this.createTime = createTime;
        this.typeId = typeId;
        this.status = status;
    }

    public ArticleStatus() {
    }
}
