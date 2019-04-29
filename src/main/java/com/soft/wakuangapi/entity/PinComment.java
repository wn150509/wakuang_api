package com.soft.wakuangapi.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class PinComment {
    @Id
    @GeneratedValue
    private Integer commentId;

    private Integer pinId;
    private Integer userId;
    private String commentContent;
    private Date commentTime;
}
