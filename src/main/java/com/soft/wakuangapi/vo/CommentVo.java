package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.SysUser;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVo {
    private SysUser sysUser;//父评论人
    private String content;//父评论内容
    private Date time;//父评论时间
    private List<CommentChildVo> commentChildVoList;//子评论人数组

    public CommentVo() {
    }

    public CommentVo(SysUser sysUser, String content, Date time, List<CommentChildVo> commentChildVoList) {
        this.sysUser = sysUser;
        this.content = content;
        this.time = time;
        this.commentChildVoList = commentChildVoList;
    }
}
