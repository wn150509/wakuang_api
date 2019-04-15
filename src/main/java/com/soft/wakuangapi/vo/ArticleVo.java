package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.SysUser;
import lombok.Data;

@Data
public class ArticleVo {
    private SysUser sysUser;
    private Articles articles;

    public ArticleVo() {
    }

    public ArticleVo(SysUser sysUser, Articles articles) {
        this.sysUser = sysUser;
        this.articles = articles;
    }
}
