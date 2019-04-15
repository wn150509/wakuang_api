package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.SysUser;
import lombok.Data;

import java.util.Date;

@Data
public class CommentChildVo {
    private SysUser childFather;
    private SysUser replyUser;
    private String content;
    private Date time;

    public CommentChildVo() {
    }

    public CommentChildVo(SysUser childFather, SysUser replyUser, String content, Date time) {
        this.childFather = childFather;
        this.replyUser = replyUser;
        this.content = content;
        this.time = time;
    }
}
