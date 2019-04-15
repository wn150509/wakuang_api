package com.soft.wakuangapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class CommentChild {
    @Id
    @GeneratedValue
    private Integer childId;

    private Integer pId;//父评论id
    private Integer userId;//评论人id
    private Integer replyuserId;//被回复人的id
    private String commentContent;
    private Date commentTime;
    private Integer articleId;

}
